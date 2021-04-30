package com.andrew121410.mc.ccminecraftbot.packets.handle.pos;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityRotationPacket;

public class OnServerEntityRotationPacket extends PacketHandler<ServerEntityRotationPacket> {

    @Override
    public void handle(ServerEntityRotationPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        if (packet.getEntityId() == ccPlayer.getEntityId()) {
            ccPlayer.getCurrentLocation().add(0, 0, 0, packet.getYaw(), packet.getPitch());
        }
    }
}
