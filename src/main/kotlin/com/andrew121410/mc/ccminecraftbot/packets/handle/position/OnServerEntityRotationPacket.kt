package com.andrew121410.mc.ccminecraftbot.packets.handle.position

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityRotationPacket

class OnServerEntityRotationPacket : PacketHandler<ServerEntityRotationPacket>() {
    override fun handle(packet: ServerEntityRotationPacket, ccBotMinecraft: CCBotMinecraft) {
        val ccPlayer = ccBotMinecraft.player
        if (packet.entityId == ccPlayer.entityId) {
            ccPlayer.currentLocation!!.add(0.0, 0.0, 0.0, packet.yaw, packet.pitch)
        }
    }
}