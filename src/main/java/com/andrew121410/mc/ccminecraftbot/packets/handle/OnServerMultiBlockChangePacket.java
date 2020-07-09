package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerMultiBlockChangePacket extends PacketHandler<ServerMultiBlockChangePacket> {

    private Main main;

    @Override
    public void handle(ServerMultiBlockChangePacket packet) {
        BlockChangeRecord[] blockChangeRecords = packet.getRecords();
        for (BlockChangeRecord blockChangeRecord : blockChangeRecords) {
            this.main.getPlayer().getChunkCache().updateBlock(blockChangeRecord.getPosition(), blockChangeRecord.getBlock());
        }
    }
}
