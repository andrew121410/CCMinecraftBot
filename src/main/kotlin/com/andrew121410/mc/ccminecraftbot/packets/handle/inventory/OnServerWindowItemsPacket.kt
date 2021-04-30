package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import lombok.AllArgsConstructor;

public class OnServerWindowItemsPacket extends PacketHandler<ServerWindowItemsPacket> {

    @Override
    public void handle(ServerWindowItemsPacket packet, CCBotMinecraft ccBotMinecraft) {
        ccBotMinecraft.getPlayer().getPlayerInventory().handleServerWindowItemsPacket(packet);
    }
}
