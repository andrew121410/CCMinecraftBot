package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerSetSlotPacket extends PacketHandler<ServerSetSlotPacket> {

    private CCBotMinecraft CCBotMinecraft;

    @Override
    public void handle(ServerSetSlotPacket packet) {
        this.CCBotMinecraft.getPlayer().getPlayerInventory().handleServerSetSlotPacket(packet);
    }
}
