package com.andrew121410.mc.ccminecraftbot.packets.handle

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket

class OnServerMultiBlockChangePacket : PacketHandler<ServerMultiBlockChangePacket>() {
    override fun handle(packet: ServerMultiBlockChangePacket, ccBotMinecraft: CCBotMinecraft) {
        val blockChangeRecords = packet.records
        for (blockChangeRecord in blockChangeRecords) {
            ccBotMinecraft.player!!.chunkCache!!.updateBlock(blockChangeRecord.position, blockChangeRecord.block)
        }
    }
}