package com.andrew121410.mc.ccminecraftbot.packets;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.github.steveice10.packetlib.packet.Packet;

public abstract class PacketHandler<T extends Packet> {

    public abstract void handle(T packet, CCBotMinecraft ccBotMinecraft);
}
