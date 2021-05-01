package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket

class OnServerPlayerChangeHeldItemPacket : PacketHandler<ServerPlayerChangeHeldItemPacket>() {
    override fun handle(packet: ServerPlayerChangeHeldItemPacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player.playerInventory.handleServerPlayerChangeHeldItemPacket(packet)
    }
}