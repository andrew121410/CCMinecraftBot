package com.andrew121410.mc.ccminecraftbot.packets.handle.chunk

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket

class OnServerChunkDataPacket : PacketHandler<ServerChunkDataPacket>() {
    override fun handle(packet: ServerChunkDataPacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player.chunkCache.addToCache(packet.column)
    }
}