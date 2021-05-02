package com.andrew121410.mc.ccminecraftbot.world.chunks

import com.andrew121410.mc.ccminecraftbot.objects.Block
import com.andrew121410.mc.ccminecraftbot.objects.BoundingBox
import com.andrew121410.mc.ccminecraftbot.utils.ResourceManager
import com.andrew121410.mc.ccminecraftbot.world.Location
import com.github.steveice10.mc.protocol.data.game.chunk.Chunk
import com.github.steveice10.mc.protocol.data.game.chunk.Column
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position

data class ChunkCache(private val chunks: MutableMap<ChunkPosition, Column> = HashMap()) {
    fun addToCache(chunk: Column): Column? {
        val chunkPosition = ChunkPosition(chunk.x, chunk.z)
        val existingChunk: Column
        if (chunk.biomeData == null) return null

        if (chunks.containsKey(chunkPosition)) { // Column is already present in cache, we can merge with existing\
            existingChunk = chunks[chunkPosition]!!
            var changed = false
            for (i in chunk.chunks.indices) { // The chunks member is final, so chunk.getChunks() will probably be inlined and then completely optimized away
                if (chunk.chunks[i] != null) {
                    existingChunk.chunks[i] = chunk.chunks[i]
                    changed = true
                }
            }
            if (changed) existingChunk else null
        } else {
            chunks[chunkPosition] = chunk
            chunk
        }
        return null
    }

    fun removeChunk(chunkX: Int, chunkZ: Int) {
        val chunkPosition = ChunkPosition(chunkX, chunkZ)
        chunks.remove(chunkPosition)
    }

    fun updateBlock(location: Location, block: Int) {
        val x = location.x.toInt()
        val y = location.y.toInt()
        val z = location.z.toInt()

        val column = getChunk(x shr 4, z shr 4) ?: return

        if (y < MINIMUM_WORLD_HEIGHT || y shr 4 > column.chunks.size - 1) {
            // Y likely goes above or below the height limit of this world
            return
        }

        val chunk = column.chunks[y shr 4] ?: return
        chunk[x and 0xF, y and 0xF, z and 0xF] = block
    }

    fun getChunk(chunkX: Int, chunkZ: Int): Column? {
        val chunkPosition = ChunkPosition(chunkX, chunkZ)
        return chunks.getOrDefault(chunkPosition, null)
    }


    fun getBlockID(location: Location): Int {
        val x = location.x.toInt()
        val y = location.y.toInt()
        val z = location.z.toInt()

        val column = getChunk(x shr 4, z shr 4) ?: return 0
        if (y < MINIMUM_WORLD_HEIGHT || y shr 4 > column.chunks.size - 1) {
            // Y likely goes above or below the height limit of this world
            return 0
        }
        val chunk: Chunk = column.chunks[y shr 4] ?: return 0
        return chunk.get(x and 0xF, y and 0xF, z and 0xF) ?: return 0
    }

    fun getBlock(location: Location): Block {
        val blockID: Int = getBlockID(location)
        val block: Block = ResourceManager.getBlockByStateID(blockID)
        return block
    }

    fun isSolid(position: Position): Boolean {
        return getBlock(Location.from(position)).boundingBox === BoundingBox.block
    }

    fun isSolid(position: Location): Boolean {
        return getBlock(position).boundingBox === BoundingBox.block
    }

    companion object {
        private const val MINIMUM_WORLD_HEIGHT = 0
    }
}
