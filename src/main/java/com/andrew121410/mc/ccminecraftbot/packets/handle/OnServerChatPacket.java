package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.TextComponent;

@AllArgsConstructor
public class OnServerChatPacket extends PacketHandler<ServerChatPacket> {

    private Main main;

    @Override
    public void handle(ServerChatPacket packet) {
        if (packet.getMessage() instanceof TextComponent && packet.getSenderUuid() != null) {
            TextComponent textComponent = (TextComponent) packet.getMessage();
            StringBuilder stringBuilder = new StringBuilder();
            textComponent.children().forEach(message -> stringBuilder.append(((TextComponent) message).content()));
            String complete = stringBuilder.toString();
            if (!complete.contains("cc")) return;
            String command = complete.substring(complete.lastIndexOf("cc") + 3);
            System.out.println("COMMAND: " + command);
            this.main.getCommandManager().onChat(command);
        }
    }
}
