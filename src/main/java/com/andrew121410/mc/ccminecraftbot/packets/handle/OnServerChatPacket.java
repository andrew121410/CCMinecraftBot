package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerChatPacket extends PacketHandler<ServerChatPacket> {

    private Main main;

    @Override
    public void handle(ServerChatPacket packet) {
        if (packet.getMessage() instanceof TextMessage && packet.getSenderUuid() != null) {
            TextMessage textMessage = (TextMessage) packet.getMessage();
            StringBuilder stringBuilder = new StringBuilder();
            textMessage.getExtra().forEach((message -> stringBuilder.append(((TextMessage) message).getText())));
            String complete = stringBuilder.toString();
            String command = complete.substring(complete.lastIndexOf("cc") + 3);
            this.main.getCommandManager().onChat(command);
        }
    }
}
