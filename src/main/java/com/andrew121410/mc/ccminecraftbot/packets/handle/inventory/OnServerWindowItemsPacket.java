package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerWindowItemsPacket extends PacketHandler<ServerWindowItemsPacket> {

    private Main main;

    @Override
    public void handle(ServerWindowItemsPacket packet) {
        this.main.getPlayer().getPlayerInventory().handleServerWindowItemsPacket(packet);
    }
}
