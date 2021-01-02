package com.andrew121410.mc.ccminecraftbot.packets;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.handle.*;
import com.andrew121410.mc.ccminecraftbot.packets.handle.inventory.*;
import com.andrew121410.mc.ccminecraftbot.packets.handle.login.OnLoginSuccessPacket;
import com.andrew121410.mc.ccminecraftbot.packets.handle.login.OnServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerKeepAlivePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.*;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import com.github.steveice10.packetlib.event.session.*;
import com.github.steveice10.packetlib.packet.Packet;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PacketSessionAdapter extends SessionAdapter {

    private Map<Class<? extends Packet>, PacketHandler<? extends Packet>> packets;

    private CCBotMinecraft CCBotMinecraft;

    public PacketSessionAdapter(CCBotMinecraft CCBotMinecraft) {
        this.CCBotMinecraft = CCBotMinecraft;
        this.packets = new HashMap<>();
        this.registerPackets();
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerUpdateLightPacket || event.getPacket() instanceof ServerKeepAlivePacket || event.getPacket() instanceof ServerUpdateTimePacket) {
            return; //Not needed
        }

        if (event.getPacket() instanceof ServerChatPacket) {
            System.out.println("CHAT PACKET: " + event.getPacket().toString());
        }

        //Handles the packets.
        if (this.packets.containsKey(event.getPacket().getClass())) {
            this.packets.get(event.getPacket().getClass()).handle(event.getPacket());
            return;
        }
    }

    @Override
    public void packetSending(PacketSendingEvent event) {
    }

    @Override
    public void packetSent(PacketSentEvent event) {
        if (event.getPacket() instanceof ClientKeepAlivePacket) {
            return; //Just spams the console.
        }
        System.out.println("SENT: " + event.getPacket().toString());
    }

    @Override
    public void packetError(PacketErrorEvent event) {
    }

    @Override
    public void connected(ConnectedEvent event) {
    }

    @Override
    public void disconnecting(DisconnectingEvent event) {
    }

    @SneakyThrows
    @Override
    public void disconnected(DisconnectedEvent event) {
        System.out.println("Disconnected: " + event.getReason());
        if (event.getCause() != null) {
            event.getCause().printStackTrace();
            return;
        }
        if (event.getReason().contains("ban")) {
            return;
        }

        if (!this.CCBotMinecraft.isShuttingDown()) {
            final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(() -> {
                System.out.println("Trying to reconnect.");
                CCBotMinecraft.setupMinecraftBot();
                executorService.shutdown();
            }, 1, 2, TimeUnit.MINUTES);
        }
    }

    private void registerPackets() {
        //Login
        this.packets.put(LoginSuccessPacket.class, new OnLoginSuccessPacket(this.CCBotMinecraft));
        this.packets.put(ServerJoinGamePacket.class, new OnServerJoinGamePacket(this.CCBotMinecraft));

        this.packets.put(ServerPlayerHealthPacket.class, new OnServerPlayerHealthPacket(this.CCBotMinecraft));
        this.packets.put(ServerPlayerPositionRotationPacket.class, new OnServerPlayerPositionRotationPacket(this.CCBotMinecraft));
        this.packets.put(ServerChatPacket.class, new OnServerChatPacket(this.CCBotMinecraft));

        //Chunks
        this.packets.put(ServerChunkDataPacket.class, new OnServerChunkDataPacket(this.CCBotMinecraft));
        this.packets.put(ServerUnloadChunkPacket.class, new OnServerUnloadChunkPacket(this.CCBotMinecraft));
        this.packets.put(ServerBlockChangePacket.class, new OnServerBlockChangePacket(this.CCBotMinecraft));
        this.packets.put(ServerMultiBlockChangePacket.class, new OnServerMultiBlockChangePacket(this.CCBotMinecraft));

        //Inv
        this.packets.put(ServerPlayerChangeHeldItemPacket.class, new OnServerPlayerChangeHeldItemPacket(this.CCBotMinecraft));
        this.packets.put(ServerSetSlotPacket.class, new OnServerSetSlotPacket(this.CCBotMinecraft));
        this.packets.put(ServerWindowItemsPacket.class, new OnServerWindowItemsPacket(this.CCBotMinecraft));
        this.packets.put(ServerConfirmTransactionPacket.class, new OnServerConfirmTransactionPacket(this.CCBotMinecraft));
        this.packets.put(ServerCloseWindowPacket.class, new OnServerCloseWindowPacket(this.CCBotMinecraft));
    }
}
