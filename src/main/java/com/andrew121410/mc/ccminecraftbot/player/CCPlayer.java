package com.andrew121410.mc.ccminecraftbot.player;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.player.inventory.PlayerInventory;
import com.andrew121410.mc.ccminecraftbot.world.Direction;
import com.andrew121410.mc.ccminecraftbot.world.Location;
import com.andrew121410.mc.ccminecraftbot.world.chunks.ChunkCache;
import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerMovementPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CCPlayer {

    public static boolean isReady = false;

    private Main main;

    private GameProfile gameProfile;
    private int entityId;

    private GameMode gameMode;
    private GameMode previousGameMode;

    private int serverViewDistance;

    private int maxPlayers;

    private ChunkCache chunkCache;

    private String[] worldNames;
    private int worldCount;
    private CompoundTag dimension;
    private String currentWorld;
    private long hashedSeed;

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    private PlayerInventory playerInventory;

    public CCPlayer(Main main, LoginSuccessPacket loginSuccessPacket) {
        this.main = main;
        this.gameProfile = loginSuccessPacket.getProfile();
        this.playerInventory = new PlayerInventory(this.main, this);
    }

    public void handleServerJoinGamePacket(ServerJoinGamePacket serverJoinGamePacket) {
        this.entityId = serverJoinGamePacket.getEntityId();
        this.gameMode = serverJoinGamePacket.getGameMode();
        this.previousGameMode = serverJoinGamePacket.getPreviousGamemode();
        this.serverViewDistance = serverJoinGamePacket.getViewDistance();
        this.maxPlayers = serverJoinGamePacket.getMaxPlayers();
        this.chunkCache = new ChunkCache(this.main);
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
        isReady = true;
    }

    public void sendPlayerMovementPacket() {
        ClientPlayerMovementPacket packet = new ClientPlayerMovementPacket(true);
        this.main.getClient().getSession().send(packet);
    }

    public void sendPlayerPositionPacket() {
        ClientPlayerPositionPacket packet = new ClientPlayerPositionPacket(true, this.x, this.y, this.z);
        this.main.getClient().getSession().send(packet);
    }

    public void sendPlayerPositionRotationPacket() {
        ClientPlayerPositionRotationPacket packet = new ClientPlayerPositionRotationPacket(true, this.x, this.y, this.z, this.yaw, this.pitch);
        this.main.getClient().getSession().send(packet);
    }

    public Location getLocation() {
        return new Location(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    public void setLocation(Location to) {
        this.x = to.getX();
        this.y = to.getY();
        this.z = to.getZ();
    }

    public Direction getDirection() {
        return Direction.getDirection(getLocation());
    }
}
