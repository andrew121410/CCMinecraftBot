package com.andrew121410.mc.ccminecraftbot.world.chunks

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position

data class ChunkPosition(
    val x: Int = 0,
    val z: Int = 0
) {
    fun getBlock(x: Int, y: Int, z: Int): Position {
        return Position((this.x shl 4) + x, y, (this.z shl 4) + z)
    }

    fun getChunkBlock(x: Int, y: Int, z: Int): Position {
        val chunkX = x and 15
        val chunkY = y and 15
        val chunkZ = z and 15
        return Position(chunkX, chunkY, chunkZ)
    }
}