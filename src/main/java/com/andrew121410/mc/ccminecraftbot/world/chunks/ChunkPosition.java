package com.andrew121410.mc.ccminecraftbot.world.chunks;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ChunkPosition {
    private int x;
    private int z;

    public Position getBlock(int x, int y, int z) {
        return new Position((this.x << 4) + x, y, (this.z << 4) + z);
    }

    public Position getChunkBlock(int x, int y, int z) {
        int chunkX = x & 15;
        int chunkY = y & 15;
        int chunkZ = z & 15;
        return new Position(chunkX, chunkY, chunkZ);
    }
}
