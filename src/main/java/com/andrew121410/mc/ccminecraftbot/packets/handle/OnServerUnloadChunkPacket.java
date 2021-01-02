package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.world.chunks.ChunkPosition;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUnloadChunkPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerUnloadChunkPacket extends PacketHandler<ServerUnloadChunkPacket> {

    private CCBotMinecraft CCBotMinecraft;

    @Override
    public void handle(ServerUnloadChunkPacket packet) {
        this.CCBotMinecraft.getPlayer().getChunkCache().removeChunk(new ChunkPosition(packet.getX(), packet.getZ()));
    }
}
