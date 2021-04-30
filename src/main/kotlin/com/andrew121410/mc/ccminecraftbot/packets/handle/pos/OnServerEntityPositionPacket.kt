package com.andrew121410.mc.ccminecraftbot.packets.handle.pos

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket

class OnServerEntityPositionPacket : PacketHandler<ServerEntityPositionPacket>() {
    override fun handle(packet: ServerEntityPositionPacket, ccBotMinecraft: CCBotMinecraft) {
        val ccPlayer = ccBotMinecraft.player!!
        if (packet.entityId == ccPlayer.entityId) {
            ccPlayer.currentLocation!!.add(
                packet.moveX / (128 * 32),
                packet.moveX / (128 * 32),
                packet.moveX / (128 * 32),
                0f,
                0f
            )
        }
    }
}