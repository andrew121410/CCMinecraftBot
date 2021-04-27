package com.andrew121410.mc.ccminecraftbot.packets.handle.pos;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket;

public class OnServerEntityPositionPacket extends PacketHandler<ServerEntityPositionPacket> {
    @Override
    public void handle(ServerEntityPositionPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        if (packet.getEntityId() == ccBotMinecraft.getPlayer().getEntityId()) {
            ccPlayer.getCurrentLocation().add(
                    packet.getMoveX() / (128 * 32),
                    packet.getMoveX() / (128 * 32),
                    packet.getMoveX() / (128 * 32),
                    0f,
                    0f);
        }
    }
}
