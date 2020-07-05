package com.andrew121410.mc.ccminecraftbot;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CCPlayer {

    private Main main;

    private GameProfile gameProfile;
    private int entityId;

    private GameMode gameMode;
    private GameMode previousGameMode;

    private int serverViewDistance;

    private int maxPlayers;

    private String[] worldNames;
    private int worldCount;
    private String dimension;
    private String currentWorld;
    private long hashedSeed;

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public CCPlayer(Main main, LoginSuccessPacket loginSuccessPacket) {
        this.gameProfile = loginSuccessPacket.getProfile();
    }

    public void handleServerJoinGamePacket(ServerJoinGamePacket serverJoinGamePacket) {
        this.entityId = serverJoinGamePacket.getEntityId();
        this.gameMode = serverJoinGamePacket.getGameMode();
        this.previousGameMode = serverJoinGamePacket.getPreviousGamemode();
        this.serverViewDistance = serverJoinGamePacket.getViewDistance();
        this.maxPlayers = serverJoinGamePacket.getMaxPlayers();
        this.worldNames = serverJoinGamePacket.getWorldNames();
        this.worldCount = serverJoinGamePacket.getWorldCount();
        this.dimension = serverJoinGamePacket.getDimension();
        this.currentWorld = serverJoinGamePacket.getWorldName();
        this.hashedSeed = serverJoinGamePacket.getHashedSeed();
    }

    public void handleServerPlayerPositionRotationPacket(ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket) {
        this.x = serverPlayerPositionRotationPacket.getX();
        this.y = serverPlayerPositionRotationPacket.getY();
        this.z = serverPlayerPositionRotationPacket.getZ();
        this.yaw = serverPlayerPositionRotationPacket.getYaw();
        this.pitch = serverPlayerPositionRotationPacket.getPitch();
    }

}
