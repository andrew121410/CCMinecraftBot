package com.andrew121410.mc.ccminecraftbot.packets.handle.position

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.andrew121410.mc.ccminecraftbot.player.MovementManager
import com.andrew121410.mc.ccminecraftbot.world.Location.Companion.from
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket

class OnServerSpawnPositionPacket : PacketHandler<ServerSpawnPositionPacket>() {
    override fun handle(packet: ServerSpawnPositionPacket, ccBotMinecraft: CCBotMinecraft) {
        val ccPlayer = ccBotMinecraft.player
        ccPlayer.currentLocation = from(packet.position)
        ccPlayer.spawnPoint = from(packet.position)
        ccPlayer.movementManager = MovementManager(ccPlayer)
        ccPlayer.movementManager!!.onJoin()
    }
}