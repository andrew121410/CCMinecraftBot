package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class OnServerJoinGamePacket extends PacketHandler<ServerJoinGamePacket> {

    private Main main;

    @Override
    public void handle(ServerJoinGamePacket packet) {
        this.main.getPlayer().handleServerJoinGamePacket(packet);
    }
}
