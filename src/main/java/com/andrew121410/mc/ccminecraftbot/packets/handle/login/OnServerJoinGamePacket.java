package com.andrew121410.mc.ccminecraftbot.packets.handle.login;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerJoinGamePacket extends PacketHandler<ServerJoinGamePacket> {

    private CCBotMinecraft CCBotMinecraft;

    @Override
    public void handle(ServerJoinGamePacket packet) {
        this.CCBotMinecraft.getPlayer().handleServerJoinGamePacket(packet);
    }
}
