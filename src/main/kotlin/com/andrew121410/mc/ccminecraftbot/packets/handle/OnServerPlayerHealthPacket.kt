package com.andrew121410.mc.ccminecraftbot.packets.handle

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.data.game.ClientRequest
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket

class OnServerPlayerHealthPacket : PacketHandler<ServerPlayerHealthPacket>() {
    override fun handle(packet: ServerPlayerHealthPacket, ccBotMinecraft: CCBotMinecraft) {
        if (packet.health.toDouble() == 0.0) {
            //Make the bot respawn on it's own.
            val clientRequestPacket = ClientRequestPacket(ClientRequest.RESPAWN)
            ccBotMinecraft.client.session.send(clientRequestPacket)
            println("Bot has respawn.")
        } else if (packet.food < 20) {
            ccBotMinecraft.player.playerInventory.findFoodAndEatIt()
        }
    }
}