package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerChunkDataPacket extends PacketHandler<ServerChunkDataPacket> {

    private Main main;

    @Override
    public void handle(ServerChunkDataPacket packet) {
        if (packet.getColumn().getBiomeData() == null)
            return; //Not a full chunk.

        this.main.getPlayer().getChunkCache().addChunk(packet.getColumn());
    }
}
