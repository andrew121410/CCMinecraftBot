package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerPlayerPositionRotationPacket extends PacketHandler<ServerPlayerPositionRotationPacket> {

    private CCBotMinecraft CCBotMinecraft;

    @Override
    public void handle(ServerPlayerPositionRotationPacket packet) {
        this.CCBotMinecraft.getPlayer().handleServerPlayerPositionRotationPacket(packet);
    }
}
