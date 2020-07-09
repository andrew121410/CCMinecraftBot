package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerPlayerChangeHeldItemPacket extends PacketHandler<ServerPlayerChangeHeldItemPacket> {

    private Main main;

    @Override
    public void handle(ServerPlayerChangeHeldItemPacket packet) {
        this.main.getPlayer().getPlayerInventory().handleServerPlayerChangeHeldItemPacket(packet);
    }
}
