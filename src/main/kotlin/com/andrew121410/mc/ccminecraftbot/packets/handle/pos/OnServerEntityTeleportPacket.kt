package com.andrew121410.mc.ccminecraftbot.packets.handle.pos

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.andrew121410.mc.ccminecraftbot.world.Location
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket

class OnServerEntityTeleportPacket : PacketHandler<ServerEntityTeleportPacket>() {
    override fun handle(packet: ServerEntityTeleportPacket, ccBotMinecraft: CCBotMinecraft) {
        val ccPlayer = ccBotMinecraft.player!!
        if (packet.entityId == ccPlayer.entityId) {
            ccPlayer.currentLocation = Location(packet.x, packet.y, packet.z, packet.yaw, packet.pitch)
        }
    }
}