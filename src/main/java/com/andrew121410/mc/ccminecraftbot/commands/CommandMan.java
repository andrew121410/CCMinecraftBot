package com.andrew121410.mc.ccminecraftbot.commands;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;

public class CommandMan {

    private Main main;

    public CommandMan(Main main) {
        this.main = main;
    }

    public void onChat(ServerChatPacket serverChatPacket, TextMessage textMessage) {
        String text = textMessage.getText();
        String filtered = text.substring(text.indexOf(":" + 1));
        filtered = filtered.replaceFirst(" ", "");

        if (filtered.equalsIgnoreCase("come here")) {
            System.out.println("Come here was a request.");
        }
    }
}
