package com.andrew121410.mc.ccminecraftbot.packets.handle.pos;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.data.game.entity.player.PositionElement;
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;

public class OnServerPlayerPositionRotationPacket extends PacketHandler<ServerPlayerPositionRotationPacket> {
    @Override
    public void handle(ServerPlayerPositionRotationPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        double deltaX = packet.getRelative().contains(PositionElement.X) ? packet.getX() : packet.getX() - ccPlayer.getCurrentLocation().getX();
        double deltaY = packet.getRelative().contains(PositionElement.Y) ? packet.getY() : packet.getY() - ccPlayer.getCurrentLocation().getY();
        double deltaZ = packet.getRelative().contains(PositionElement.Z) ? packet.getZ() : packet.getZ() - ccPlayer.getCurrentLocation().getZ();
        float deltaYaw = packet.getRelative().contains(PositionElement.YAW) ? packet.getYaw() : packet.getYaw() - ccPlayer.getCurrentLocation().getYaw();
        float deltaPitch = packet.getRelative().contains(PositionElement.PITCH) ? packet.getPitch() : packet.getPitch() - ccPlayer.getCurrentLocation().getPitch();
        ccPlayer.getCurrentLocation().add(deltaX, deltaY, deltaZ, deltaYaw, deltaPitch);
        ccBotMinecraft.getClient().getSession().send(new ClientTeleportConfirmPacket(packet.getTeleportId()));
    }
}
