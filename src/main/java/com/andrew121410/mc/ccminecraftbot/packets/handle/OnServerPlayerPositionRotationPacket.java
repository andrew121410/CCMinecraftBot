package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class OnServerPlayerPositionRotationPacket extends PacketHandler<ServerPlayerPositionRotationPacket> {

    private Main main;

    @Override
    public void handle(ServerPlayerPositionRotationPacket packet) {
        this.main.getPlayer().handleServerPlayerPositionRotationPacket(packet);
    }
}
