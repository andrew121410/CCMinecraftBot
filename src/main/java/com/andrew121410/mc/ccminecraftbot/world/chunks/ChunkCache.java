package com.andrew121410.mc.ccminecraftbot.world.chunks;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
public class ChunkCache {

    private Map<ChunkPosition, Column> chunks;

    private Main main;

    public ChunkCache(Main main) {
        this.main = main;
        this.chunks = new HashMap<>();
    }

    public void addChunk(Column column) {
        ChunkPosition position = new ChunkPosition(column.getX(), column.getZ());
        this.chunks.put(position, column);
    }

    public void removeChunk(ChunkPosition chunkPosition) {
        this.chunks.remove(chunkPosition);
    }

    public void updateBlock(Position position, int block) {
        ChunkPosition chunkPosition = new ChunkPosition(position.getX() >> 4, position.getZ() >> 4);
        if (!chunks.containsKey(chunkPosition)) {
            return;
        }
        Column column = chunks.get(chunkPosition);
        Chunk chunk = column.getChunks()[position.getY() >> 4];
        Position blockPosition = chunkPosition.getChunkBlock(position.getX(), position.getY(), position.getZ());
        if (chunk != null) {
            chunk.set(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ(), block);
        }
    }

    public Integer getBlockAt(Position position) {
        ChunkPosition chunkPosition = new ChunkPosition(position.getX() >> 4, position.getZ() >> 4);
        if (!chunks.containsKey(chunkPosition)) {
            return null;
        }
        Column column = chunks.get(chunkPosition);
        Chunk chunk = column.getChunks()[position.getY() >> 4];
        Position blockPosition = chunkPosition.getChunkBlock(position.getX(), position.getY(), position.getZ());
        if (chunk == null) {
            return null;
        }
        return chunk.get(blockPosition.getX(), blockPosition.getY(), blockPosition.getZ());
    }
}
