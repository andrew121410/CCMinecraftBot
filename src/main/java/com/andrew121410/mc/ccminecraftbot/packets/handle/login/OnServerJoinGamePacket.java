package com.andrew121410.mc.ccminecraftbot.packets.handle.login;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;

public class OnServerJoinGamePacket extends PacketHandler<ServerJoinGamePacket> {

    @Override
    public void handle(ServerJoinGamePacket packet, CCBotMinecraft ccBotMinecraft) {
        ccBotMinecraft.getPlayer().handleServerJoinGamePacket(packet);
    }
}
