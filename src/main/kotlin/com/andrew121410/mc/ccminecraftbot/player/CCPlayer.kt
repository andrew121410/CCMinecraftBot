package com.andrew121410.mc.ccminecraftbot.player

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.player.inventory.PlayerInventory
import com.andrew121410.mc.ccminecraftbot.world.Direction
import com.andrew121410.mc.ccminecraftbot.world.Location
import com.andrew121410.mc.ccminecraftbot.world.chunks.ChunkCache
import com.github.steveice10.mc.auth.data.GameProfile
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket
import com.github.steveice10.opennbt.tag.builtin.CompoundTag
import com.github.steveice10.packetlib.Client
import lombok.*

@Getter
@Setter
@ToString
@EqualsAndHashCode
class CCPlayer(private val CCBotMinecraft: CCBotMinecraft, loginSuccessPacket: LoginSuccessPacket) {
    var client: Client = CCBotMinecraft.client!!
    private val gameProfile: GameProfile
    var entityId = 0
    private var gameMode: GameMode? = null
    private var previousGameMode: GameMode? = null
    private var serverViewDistance = 0
    private var maxPlayers = 0
    var chunkCache: ChunkCache? = null
    private lateinit var worldNames: Array<String>
    private var worldCount = 0
    private var dimension: CompoundTag? = null
    private var currentWorld: String? = null
    private var hashedSeed: Long = 0
    var spawnPoint: Location? = null
    var currentLocation: Location? = null
    var movementManager: MovementManager? = null
    val playerInventory: PlayerInventory
    fun handleServerJoinGamePacket(serverJoinGamePacket: ServerJoinGamePacket) {
        entityId = serverJoinGamePacket.entityId
        gameMode = serverJoinGamePacket.gameMode
        previousGameMode = serverJoinGamePacket.previousGamemode
        serverViewDistance = serverJoinGamePacket.viewDistance
        maxPlayers = serverJoinGamePacket.maxPlayers
        chunkCache = ChunkCache(CCBotMinecraft)
        worldNames = serverJoinGamePacket.worldNames
        worldCount = serverJoinGamePacket.worldCount
        dimension = serverJoinGamePacket.dimension
        currentWorld = serverJoinGamePacket.worldName
        hashedSeed = serverJoinGamePacket.hashedSeed
    }

    val direction: Direction?
        get() = Direction.getDirection(currentLocation)

    companion object {
        var isReady = false
    }

    init {
        gameProfile = loginSuccessPacket.profile
        playerInventory = PlayerInventory(CCBotMinecraft, this)
    }
}