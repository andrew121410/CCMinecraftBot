package com.andrew121410.mc.ccminecraftbot.packets.handle.pos;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket;

public class OnServerEntityPositionRotationPacket extends PacketHandler<ServerEntityPositionRotationPacket> {
    @Override
    public void handle(ServerEntityPositionRotationPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        if (packet.getEntityId() == ccPlayer.getEntityId()) {
            ccPlayer.getCurrentLocation().add(
                    packet.getMoveX() / (128 * 32),
                    packet.getMoveX() / (128 * 32),
                    packet.getMoveX() / (128 * 32),
                    packet.getYaw(),
                    packet.getPitch());
        }
    }
}
