package com.andrew121410.mc.ccminecraftbot.world;

public enum Direction {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public static Direction getDirection(Location location) {
        double rotation = (location.getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        if ((0.0D <= rotation) && (rotation < 22.5D)) {
            return NORTH;
        }
        if ((22.5D <= rotation) && (rotation < 67.5D)) {
            return NORTH_EAST;
        }
        if ((67.5D <= rotation) && (rotation < 112.5D)) {
            return EAST;
        }
        if ((112.5D <= rotation) && (rotation < 157.5D)) {
            return SOUTH_EAST;
        }
        if ((157.5D <= rotation) && (rotation < 202.5D)) {
            return SOUTH;
        }
        if ((202.5D <= rotation) && (rotation < 247.5D)) {
            return SOUTH_WEST;
        }
        if ((247.5D <= rotation) && (rotation < 292.5D)) {
            return WEST;
        }
        if ((292.5D <= rotation) && (rotation < 337.5D)) {
            return NORTH_WEST;
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return NORTH;
        }
        return null;
    }
}
