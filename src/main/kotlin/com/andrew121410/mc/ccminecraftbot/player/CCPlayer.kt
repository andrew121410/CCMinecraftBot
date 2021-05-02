package com.andrew121410.mc.ccminecraftbot.player

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.player.inventory.PlayerInventory
import com.andrew121410.mc.ccminecraftbot.world.Direction
import com.andrew121410.mc.ccminecraftbot.world.Location
import com.github.steveice10.mc.auth.data.GameProfile
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket
import com.github.steveice10.opennbt.tag.builtin.CompoundTag
import com.github.steveice10.packetlib.Client
import com.andrew121410.mc.ccminecraftbot.world.chunks.ChunkCache

class CCPlayer(private val CCBotMinecraft: CCBotMinecraft, loginSuccessPacket: LoginSuccessPacket) {
    var client: Client = CCBotMinecraft.client
    private val gameProfile: GameProfile = loginSuccessPacket.profile
    var entityId = 0
    var gameMode: GameMode? = null
    var previousGameMode: GameMode? = null
    var serverViewDistance = 0
    var maxPlayers = 0
    lateinit var chunkCache: ChunkCache
    lateinit var worldNames: Array<String>
    var worldCount = 0
    var dimension: CompoundTag? = null
    var currentWorld: String? = null
    var hashedSeed: Long = 0
    var spawnPoint: Location? = null
    var currentLocation: Location? = null
    var movementManager: MovementManager? = null
    val playerInventory: PlayerInventory = PlayerInventory(CCBotMinecraft, this)
    fun handleServerJoinGamePacket(serverJoinGamePacket: ServerJoinGamePacket) {
        entityId = serverJoinGamePacket.entityId
        gameMode = serverJoinGamePacket.gameMode
        previousGameMode = serverJoinGamePacket.previousGamemode
        serverViewDistance = serverJoinGamePacket.viewDistance
        maxPlayers = serverJoinGamePacket.maxPlayers
        chunkCache = ChunkCache()
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
}