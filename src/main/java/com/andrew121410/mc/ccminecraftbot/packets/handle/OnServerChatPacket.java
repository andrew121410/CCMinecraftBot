package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class OnServerChatPacket extends PacketHandler<ServerChatPacket> {

    private Main main;

    //    @TODO most likely will be broken later so fix this crazy code.
    @Override
    public void handle(ServerChatPacket packet) {
        if (packet.getMessage() instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) packet.getMessage();
            StringBuilder stringBuilder = new StringBuilder();
            textMessage.getExtra().forEach((message -> stringBuilder.append(((TextMessage) message).getText())));
            System.out.println("Text received: " + stringBuilder.toString());
        }
    }
}
