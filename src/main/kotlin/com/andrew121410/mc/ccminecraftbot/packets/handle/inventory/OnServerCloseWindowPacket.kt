package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket

class OnServerCloseWindowPacket : PacketHandler<ServerCloseWindowPacket>() {
    override fun handle(packet: ServerCloseWindowPacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player.playerInventory.closeWindow(packet.windowId)
    }
}