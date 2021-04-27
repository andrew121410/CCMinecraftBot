package com.andrew121410.mc.ccminecraftbot.packets.handle.pos;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.world.Location;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket;

public class OnServerEntityTeleportPacket extends PacketHandler<ServerEntityTeleportPacket> {
    @Override
    public void handle(ServerEntityTeleportPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        if (packet.getEntityId() == ccBotMinecraft.getPlayer().getEntityId()) {
            ccPlayer.setCurrentLocation(new Location(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch()));
        }
    }
}
