package com.andrew121410.mc.ccminecraftbot.packets.handle

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket

class OnServerBlockChangePacket : PacketHandler<ServerBlockChangePacket>() {
    override fun handle(packet: ServerBlockChangePacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player.chunkCache.updateBlock(packet.record.position, packet.record.block)
    }
}