package com.andrew121410.mc.ccminecraftbot.packets.handle.chunk

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.andrew121410.mc.ccminecraftbot.world.Location
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket

class OnServerMultiBlockChangePacket : PacketHandler<ServerMultiBlockChangePacket>() {
    override fun handle(packet: ServerMultiBlockChangePacket, ccBotMinecraft: CCBotMinecraft) {
        val blockChangeRecords = packet.records
        for (blockChangeRecord in blockChangeRecords) {
            ccBotMinecraft.player.chunkCache.updateBlock(Location.from(blockChangeRecord.position), blockChangeRecord.block)
        }
    }
}