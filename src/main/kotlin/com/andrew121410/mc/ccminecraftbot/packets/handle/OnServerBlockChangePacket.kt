package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;

public class OnServerBlockChangePacket extends PacketHandler<ServerBlockChangePacket> {
    @Override
    public void handle(ServerBlockChangePacket packet, CCBotMinecraft ccBotMinecraft) {
        ccBotMinecraft.getPlayer().getChunkCache().updateBlock(packet.getRecord().getPosition(), packet.getRecord().getBlock());
    }
}