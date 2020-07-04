package com.andrew121410.mc.ccminecraftbot.packets;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.event.session.*;

public class PacketSessionAdapter extends SessionAdapter {

    private Main main;

    public PacketSessionAdapter(Main main) {
        this.main = main;
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerChatPacket) {
            ServerChatPacket chatPacket = event.getPacket();
            if (chatPacket.getMessage() instanceof TextMessage) {
                TextMessage message = (TextMessage) chatPacket.getMessage();
                event.getSession().send(new ClientChatPacket(message.getText()));
                if (message.getText().contains("andrew121410") && message.getText().startsWith(">>")) {
                    this.main.getCommandManager().onChat(chatPacket, message);
                }
                return;
            }
        }
        System.out.println("RECEIVED: " + event.getPacket().toString());
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

    @Override
    public void disconnected(DisconnectedEvent event) {
        System.out.println("Disconnected: " + event.getReason());
        if (event.getCause() != null) {
            event.getCause().printStackTrace();
        }
    }
}
