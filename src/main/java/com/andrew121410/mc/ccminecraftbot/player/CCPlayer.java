package com.andrew121410.mc.ccminecraftbot.player;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.player.inventory.PlayerInventory;
import com.andrew121410.mc.ccminecraftbot.world.Direction;
import com.andrew121410.mc.ccminecraftbot.world.Location;
import com.andrew121410.mc.ccminecraftbot.world.chunks.ChunkCache;
import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.packetlib.Client;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CCPlayer {

    public static boolean isReady = false;

    private CCBotMinecraft CCBotMinecraft;
    public Client client;


    private GameProfile gameProfile;
    private int entityId;

    private GameMode gameMode;
    private GameMode previousGameMode;

    private int serverViewDistance;

    private int maxPlayers;

    public ChunkCache chunkCache;

    private String[] worldNames;
    private int worldCount;
    private CompoundTag dimension;
    private String currentWorld;
    private long hashedSeed;

    private Location spawnPoint;
    public Location currentLocation;

    public MovementManager movementManager;

    private PlayerInventory playerInventory;

    public CCPlayer(CCBotMinecraft CCBotMinecraft, LoginSuccessPacket loginSuccessPacket) {
        this.CCBotMinecraft = CCBotMinecraft;
        this.client = CCBotMinecraft.getClient();
        this.gameProfile = loginSuccessPacket.getProfile();
        this.playerInventory = new PlayerInventory(this.CCBotMinecraft, this);
        this.movementManager = new MovementManager(this);
    }

    public void handleServerJoinGamePacket(ServerJoinGamePacket serverJoinGamePacket) {
        this.entityId = serverJoinGamePacket.getEntityId();
        this.gameMode = serverJoinGamePacket.getGameMode();
        this.previousGameMode = serverJoinGamePacket.getPreviousGamemode();
        this.serverViewDistance = serverJoinGamePacket.getViewDistance();
        this.maxPlayers = serverJoinGamePacket.getMaxPlayers();
        this.chunkCache = new ChunkCache(this.CCBotMinecraft);
        this.worldNames = serverJoinGamePacket.getWorldNames();
        this.worldCount = serverJoinGamePacket.getWorldCount();
        this.dimension = serverJoinGamePacket.getDimension();
        this.currentWorld = serverJoinGamePacket.getWorldName();
        this.hashedSeed = serverJoinGamePacket.getHashedSeed();
    }

    public Direction getDirection() {
        return Direction.getDirection(this.currentLocation);
    }
}
