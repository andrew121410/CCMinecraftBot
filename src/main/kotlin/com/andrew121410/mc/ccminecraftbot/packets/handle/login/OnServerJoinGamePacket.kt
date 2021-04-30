package com.andrew121410.mc.ccminecraftbot.packets.handle.login

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket

class OnServerJoinGamePacket : PacketHandler<ServerJoinGamePacket>() {
    override fun handle(packet: ServerJoinGamePacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player!!.handleServerJoinGamePacket(packet)
    }
}