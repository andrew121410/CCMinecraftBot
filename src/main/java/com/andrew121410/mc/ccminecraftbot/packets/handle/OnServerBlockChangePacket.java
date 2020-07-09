package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerBlockChangePacket extends PacketHandler<ServerBlockChangePacket> {

    private Main main;

    @Override
    public void handle(ServerBlockChangePacket packet) {
        this.main.getPlayer().getChunkCache().updateBlock(packet.getRecord().getPosition(), packet.getRecord().getBlock());
    }
}
