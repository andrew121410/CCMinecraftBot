package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import lombok.AllArgsConstructor;

public class OnServerMultiBlockChangePacket extends PacketHandler<ServerMultiBlockChangePacket> {

    @Override
    public void handle(ServerMultiBlockChangePacket packet, CCBotMinecraft ccBotMinecraft) {
        BlockChangeRecord[] blockChangeRecords = packet.getRecords();
        for (BlockChangeRecord blockChangeRecord : blockChangeRecords) {
            ccBotMinecraft.getPlayer().getChunkCache().updateBlock(blockChangeRecord.getPosition(), blockChangeRecord.getBlock());
        }
    }
}
