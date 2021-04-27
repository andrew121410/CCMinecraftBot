package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import net.kyori.adventure.text.TextComponent;

public class OnServerChatPacket extends PacketHandler<ServerChatPacket> {

    private String format = ":";

    @Override
    public void handle(ServerChatPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        if (packet.getMessage() instanceof TextComponent && packet.getSenderUuid() != null) {
            TextComponent textComponent = (TextComponent) packet.getMessage();
            StringBuilder stringBuilder = new StringBuilder();
            textComponent.children().forEach(message -> stringBuilder.append(((TextComponent) message).content()));
            String complete = stringBuilder.toString();
            if (!complete.contains("ccbot")) return;
            String[] args = complete.split(" ");
            args[0] = args[0].replaceAll(format, "");
            String sender = args[0];
            String command = complete.substring(complete.lastIndexOf("ccbot") + 6);
            System.out.println("COMMAND: " + command);
            ccBotMinecraft.getCommandManager().onChat(sender, command);
        }
    }
}
