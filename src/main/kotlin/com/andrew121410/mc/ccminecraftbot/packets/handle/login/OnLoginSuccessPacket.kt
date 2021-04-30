package com.andrew121410.mc.ccminecraftbot.packets.handle.login

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket

class OnLoginSuccessPacket : PacketHandler<LoginSuccessPacket>() {
    override fun handle(packet: LoginSuccessPacket, ccBotMinecraft: CCBotMinecraft) {
        ccBotMinecraft.player = CCPlayer(ccBotMinecraft, packet)
    }
}