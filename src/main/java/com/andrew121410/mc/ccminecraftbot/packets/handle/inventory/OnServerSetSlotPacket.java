package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import lombok.AllArgsConstructor;

public class OnServerSetSlotPacket extends PacketHandler<ServerSetSlotPacket> {

    @Override
    public void handle(ServerSetSlotPacket packet, CCBotMinecraft ccBotMinecraft) {
        ccBotMinecraft.getPlayer().getPlayerInventory().handleServerSetSlotPacket(packet);
    }
}
