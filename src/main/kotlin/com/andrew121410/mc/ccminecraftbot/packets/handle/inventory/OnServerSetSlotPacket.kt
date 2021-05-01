package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket

class OnServerSetSlotPacket : PacketHandler<ServerSetSlotPacket>() {
    override fun handle(packet: ServerSetSlotPacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player.playerInventory.handleServerSetSlotPacket(packet)
    }
}