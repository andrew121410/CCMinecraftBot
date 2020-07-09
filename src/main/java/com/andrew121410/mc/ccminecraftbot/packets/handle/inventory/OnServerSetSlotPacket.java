package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerSetSlotPacket extends PacketHandler<ServerSetSlotPacket> {

    private Main main;

    @Override
    public void handle(ServerSetSlotPacket packet) {
        this.main.getPlayer().getPlayerInventory().handleServerSetSlotPacket(packet);
    }
}
