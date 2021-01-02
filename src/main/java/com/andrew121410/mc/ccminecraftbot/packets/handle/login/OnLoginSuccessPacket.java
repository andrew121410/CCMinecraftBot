package com.andrew121410.mc.ccminecraftbot.packets.handle.login;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnLoginSuccessPacket extends PacketHandler<LoginSuccessPacket> {

    private CCBotMinecraft CCBotMinecraft;

    @Override
    public void handle(LoginSuccessPacket packet) {
        this.CCBotMinecraft.setPlayer(new CCPlayer(this.CCBotMinecraft, packet));
    }
}
