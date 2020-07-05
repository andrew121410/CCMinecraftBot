package com.andrew121410.mc.ccminecraftbot.packets;

import com.andrew121410.mc.ccminecraftbot.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.Main;
import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateLightPacket;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import com.github.steveice10.packetlib.event.session.*;
import lombok.SneakyThrows;

public class PacketSessionAdapter extends SessionAdapter {

    private Main main;

    public PacketSessionAdapter(Main main) {
        this.main = main;
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerUpdateLightPacket || event.getPacket() instanceof ServerChunkDataPacket || event.getPacket() instanceof ServerMultiBlockChangePacket || event.getPacket() instanceof ServerBlockChangePacket) {
            return; //Not needed
        }

        //Login
        if (event.getPacket() instanceof LoginSuccessPacket) {
            LoginSuccessPacket loginSuccessPacket = event.getPacket();
            this.main.setCcPlayer(new CCPlayer(this.main, loginSuccessPacket));
            return;
        } else if (event.getPacket() instanceof ServerJoinGamePacket) {
            ServerJoinGamePacket serverJoinGamePacket = event.getPacket();
            this.main.getCcPlayer().handleServerJoinGamePacket(serverJoinGamePacket);
            return;
        } else if (event.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket = event.getPacket();
            this.main.getCcPlayer().handleServerPlayerPositionRotationPacket(serverPlayerPositionRotationPacket);
        }

        System.out.println("RECEIVED: " + event.getPacket().toString());

        if (event.getPacket() instanceof ServerPlayerHealthPacket) {
            ServerPlayerHealthPacket serverPlayerHealthPacket = event.getPacket();
            if (serverPlayerHealthPacket.getHealth() == 0.0) {
                //Make the bot respawn on it's own.
                ClientRequestPacket clientRequestPacket = new ClientRequestPacket(ClientRequest.RESPAWN);
                event.getSession().send(clientRequestPacket);
                System.out.println("Bot has respawn.");
            }
            return;
        }
    }

    @Override
    public void packetSending(PacketSendingEvent event) {
    }

    @Override
    public void packetSent(PacketSentEvent event) {
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
        }
    }
}
