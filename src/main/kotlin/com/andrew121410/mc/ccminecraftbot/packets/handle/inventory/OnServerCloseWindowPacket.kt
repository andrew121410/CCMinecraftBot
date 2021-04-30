package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket;

public class OnServerCloseWindowPacket extends PacketHandler<ServerCloseWindowPacket> {

    @Override
    public void handle(ServerCloseWindowPacket packet, CCBotMinecraft ccBotMinecraft) {
        ccBotMinecraft.getPlayer().getPlayerInventory().closeWindow(packet.getWindowId());
    }
}
