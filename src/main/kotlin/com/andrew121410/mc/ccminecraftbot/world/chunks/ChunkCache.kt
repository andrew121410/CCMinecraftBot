package com.andrew121410.mc.ccminecraftbot.world.chunks

import com.andrew121410.mc.ccminecraftbot.objects.Block
import com.andrew121410.mc.ccminecraftbot.objects.BoundingBox
import com.andrew121410.mc.ccminecraftbot.utils.ResourceManager.blocks
import com.andrew121410.mc.ccminecraftbot.world.Location
import com.github.steveice10.mc.protocol.data.game.chunk.Column
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position

data class ChunkCache(private val chunks: MutableMap<ChunkPosition, Column> = HashMap()) {

    fun addChunk(column: Column) {
        val position = ChunkPosition(column.x, column.z)
        chunks[position] = column
    }

    fun removeChunk(chunkPosition: ChunkPosition) {
        chunks.remove(chunkPosition)
    }

    fun updateBlock(position: Position?, block: Int) {
        if (position == null) return
        val chunkPosition = ChunkPosition(position.x shr 4, position.z shr 4)
        val column = chunks[chunkPosition] ?: return
        val chunk = column.chunks[position.y shr 4]
        val blockPosition = chunkPosition.getChunkBlock(position.x, position.y, position.z)
        chunk[blockPosition.x, blockPosition.y, blockPosition.z] = block
    }

    fun getBlockID(position: Position?): Int? {
        if (position == null) return null
        val chunkPosition = ChunkPosition(position.x shr 4, position.z shr 4)
        if (!chunks.containsKey(chunkPosition)) {
            return null
        }
        val column = chunks[chunkPosition]
        val chunk = column!!.chunks[position.y shr 4]
        val blockPosition = chunkPosition.getChunkBlock(position.x, position.y, position.z)
        return chunk[blockPosition.x, blockPosition.y, blockPosition.z]
    }

    fun getBlock(position: Position?): Block? {
        return blocks[getBlockID(position)]
    }

    fun getBlock(location: Location): Block? {
        return blocks[getBlockID(location.toPosition())]
    }

    fun isSolid(position: Position?): Boolean {
        return getBlock(position)!!.boundingBox === BoundingBox.block
    }

    fun isSolid(position: Location): Boolean {
        return getBlock(position)!!.boundingBox === BoundingBox.block
    }
}