package com.andrew121410.mc.ccminecraftbot.packets.handle

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.andrew121410.mc.ccminecraftbot.world.chunks.ChunkPosition
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUnloadChunkPacket

class OnServerUnloadChunkPacket : PacketHandler<ServerUnloadChunkPacket>() {
    override fun handle(packet: ServerUnloadChunkPacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player!!.chunkCache!!.removeChunk(ChunkPosition(packet.x, packet.z))
    }
}