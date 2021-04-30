package com.andrew121410.mc.ccminecraftbot.packets

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.github.steveice10.packetlib.packet.Packet

abstract class PacketHandler<T : Packet?> {
    abstract fun handle(packet: T, ccBotMinecraft: CCBotMinecraft)
}