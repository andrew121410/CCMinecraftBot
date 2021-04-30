package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket

class OnServerWindowItemsPacket : PacketHandler<ServerWindowItemsPacket>() {
    override fun handle(packet: ServerWindowItemsPacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player!!.playerInventory.handleServerWindowItemsPacket(packet)
    }
}