package com.andrew121410.mc.ccminecraftbot.packets.handle.position

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.data.game.entity.player.PositionElement
import com.github.steveice10.mc.protocol.packet.ingame.client.world.ClientTeleportConfirmPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket

class OnServerPlayerPositionRotationPacket : PacketHandler<ServerPlayerPositionRotationPacket>() {
    override fun handle(packet: ServerPlayerPositionRotationPacket, ccBotMinecraft: CCBotMinecraft) {
        val ccPlayer = ccBotMinecraft.player
        if (ccPlayer.currentLocation == null) {
            println("ERROR: ServerPlayerPositionRotationPacket -> getCurrentLocation is still null.")
            return
        }
        val deltaX =
            if (packet.relative.contains(PositionElement.X)) packet.x else packet.x - ccPlayer.currentLocation!!.x
        val deltaY =
            if (packet.relative.contains(PositionElement.Y)) packet.y else packet.y - ccPlayer.currentLocation!!.y
        val deltaZ =
            if (packet.relative.contains(PositionElement.Z)) packet.z else packet.z - ccPlayer.currentLocation!!.z
        val deltaYaw =
            if (packet.relative.contains(PositionElement.YAW)) packet.yaw else packet.yaw - ccPlayer.currentLocation!!.yaw
        val deltaPitch =
            if (packet.relative.contains(PositionElement.PITCH)) packet.pitch else packet.pitch - ccPlayer.currentLocation!!.pitch
        ccPlayer.currentLocation!!.add(deltaX, deltaY, deltaZ, deltaYaw, deltaPitch)
        ccBotMinecraft.client.session.send(ClientTeleportConfirmPacket(packet.teleportId))
    }
}