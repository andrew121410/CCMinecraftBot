package com.andrew121410.mc.ccminecraftbot.packets.handle.login;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;

public class OnLoginSuccessPacket extends PacketHandler<LoginSuccessPacket> {

    @Override
    public void handle(LoginSuccessPacket packet, CCBotMinecraft ccBotMinecraft) {
        ccBotMinecraft.setPlayer(new CCPlayer(ccBotMinecraft, packet));
    }
}
