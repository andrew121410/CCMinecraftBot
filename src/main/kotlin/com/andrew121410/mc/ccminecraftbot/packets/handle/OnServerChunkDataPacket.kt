package com.andrew121410.mc.ccminecraftbot.packets.handle

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket

class OnServerChunkDataPacket : PacketHandler<ServerChunkDataPacket>() {
    override fun handle(packet: ServerChunkDataPacket, ccBotMinecraft: CCBotMinecraft) {
        if (packet.column.biomeData == null) return  //Not a full chunk.
        ccBotMinecraft.player.chunkCache.addChunk(packet.column)
    }
}