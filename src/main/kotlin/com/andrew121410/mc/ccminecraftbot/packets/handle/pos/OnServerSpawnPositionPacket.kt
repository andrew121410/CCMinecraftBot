package com.andrew121410.mc.ccminecraftbot.packets.handle.pos;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.player.MovementManager;
import com.andrew121410.mc.ccminecraftbot.world.Location;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerSpawnPositionPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerSpawnPositionPacket extends PacketHandler<ServerSpawnPositionPacket> {

    @Override
    public void handle(ServerSpawnPositionPacket packet, CCBotMinecraft ccBotMinecraft) {
        CCPlayer ccPlayer = ccBotMinecraft.getPlayer();
        ccPlayer.setCurrentLocation(Location.Companion.from(packet.getPosition()));
        ccPlayer.setSpawnPoint(Location.Companion.from(packet.getPosition()));
        ccPlayer.setMovementManager(new MovementManager(ccPlayer));
    }
}
