package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket

class OnServerConfirmTransactionPacket : PacketHandler<ServerConfirmTransactionPacket>() {
    override fun handle(packet: ServerConfirmTransactionPacket, ccBotMinecraft: CCBotMinecraft) {
        if (!packet.isAccepted) {
            val confirmPacket = ClientConfirmTransactionPacket(packet.windowId, packet.actionId, true)
            ccBotMinecraft.client.session.send(confirmPacket)
        }
    }
}