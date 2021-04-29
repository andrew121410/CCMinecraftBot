package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.commands.CommandManager;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import net.kyori.adventure.text.TextComponent;

public class OnServerChatPacket extends PacketHandler<ServerChatPacket> {

    @Override
    public void handle(ServerChatPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        if (packet.getMessage() instanceof TextComponent) {
            TextComponent textComponent = (TextComponent) packet.getMessage();
            StringBuilder stringBuilder = new StringBuilder();
            textComponent.children().forEach(message -> stringBuilder.append(((TextComponent) message).content()));
            String complete = stringBuilder.toString();
            if (!complete.contains("ccbot")) return;
            String[] args = complete.split(" ");
            args[0] = args[0].replaceAll(":", "");
            String sender = args[0];
            String command;
            try {
                command = complete.substring(complete.lastIndexOf("ccbot") + 6);
            } catch (IndexOutOfBoundsException exception) {
                CommandManager.sendMessage("IndexOutOfBoundsException was thrown. Something went wrong!");
                return;
            }
            System.out.println("COMMAND: " + command);
            ccBotMinecraft.getCommandManager().onChat(sender, command);
        }
    }
}
