package com.andrew121410.mc.ccminecraftbot.packets.handle.login;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnLoginSuccessPacket extends PacketHandler<LoginSuccessPacket> {

    private Main main;

    @Override
    public void handle(LoginSuccessPacket packet) {
        this.main.setPlayer(new CCPlayer(this.main, packet));
    }
}
