package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket;
import lombok.AllArgsConstructor;

public class OnServerPlayerChangeHeldItemPacket extends PacketHandler<ServerPlayerChangeHeldItemPacket> {

    @Override
    public void handle(ServerPlayerChangeHeldItemPacket packet, CCBotMinecraft ccBotMinecraft) {
        ccBotMinecraft.getPlayer().getPlayerInventory().handleServerPlayerChangeHeldItemPacket(packet);
    }
}
