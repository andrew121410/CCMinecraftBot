package com.andrew121410.mc.ccminecraftbot.packets;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.handle.*;
import com.andrew121410.mc.ccminecraftbot.packets.handle.inventory.*;
import com.andrew121410.mc.ccminecraftbot.packets.handle.login.OnLoginSuccessPacket;
import com.andrew121410.mc.ccminecraftbot.packets.handle.login.OnServerJoinGamePacket;
import com.andrew121410.mc.ccminecraftbot.packets.handle.pos.*;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerKeepAlivePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket;
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
        //Handles the packets.
        if (this.packets.containsKey(event.getPacket().getClass())) {
            this.packets.get(event.getPacket().getClass()).handle(event.getPacket(), this.CCBotMinecraft);
        }
    }

    @Override
    public void packetSending(PacketSendingEvent event) {
    }

    @Override
    public void packetSent(PacketSentEvent event) {
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
        this.packets.put(LoginSuccessPacket.class, new OnLoginSuccessPacket());
        this.packets.put(ServerJoinGamePacket.class, new OnServerJoinGamePacket());

        this.packets.put(ServerPlayerHealthPacket.class, new OnServerPlayerHealthPacket());
        this.packets.put(ServerChatPacket.class, new OnServerChatPacket());

        //Chunks
        this.packets.put(ServerChunkDataPacket.class, new OnServerChunkDataPacket());
        this.packets.put(ServerUnloadChunkPacket.class, new OnServerUnloadChunkPacket());
        this.packets.put(ServerBlockChangePacket.class, new OnServerBlockChangePacket());
        this.packets.put(ServerMultiBlockChangePacket.class, new OnServerMultiBlockChangePacket());

        //Inv
        this.packets.put(ServerPlayerChangeHeldItemPacket.class, new OnServerPlayerChangeHeldItemPacket());
        this.packets.put(ServerSetSlotPacket.class, new OnServerSetSlotPacket());
        this.packets.put(ServerWindowItemsPacket.class, new OnServerWindowItemsPacket());
        this.packets.put(ServerConfirmTransactionPacket.class, new OnServerConfirmTransactionPacket());
        this.packets.put(ServerCloseWindowPacket.class, new OnServerCloseWindowPacket());

        //Position
        this.packets.put(ServerEntityPositionPacket.class, new OnServerEntityPositionPacket());
        this.packets.put(ServerEntityTeleportPacket.class, new OnServerEntityTeleportPacket());
        this.packets.put(ServerPlayerPositionRotationPacket.class, new OnServerPlayerPositionRotationPacket());
        this.packets.put(ServerSpawnPositionPacket.class, new OnServerSpawnPositionPacket());
        this.packets.put(ServerEntityRotationPacket.class, new OnServerEntityRotationPacket());
        this.packets.put(ServerEntityPositionRotationPacket.class, new OnServerEntityPositionRotationPacket());
    }
}