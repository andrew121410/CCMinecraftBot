package com.andrew121410.mc.ccminecraftbot.world

enum class Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    companion object {
        fun getDirection(location: Location?): Direction? {
            var rotation = ((location!!.yaw - 90.0f) % 360.0f).toDouble()
            if (rotation < 0.0) {
                rotation += 360.0
            }
            if (0.0 <= rotation && rotation < 22.5) {
                return NORTH
            }
            if (22.5 <= rotation && rotation < 67.5) {
                return NORTH_EAST
            }
            if (67.5 <= rotation && rotation < 112.5) {
                return EAST
            }
            if (112.5 <= rotation && rotation < 157.5) {
                return SOUTH_EAST
            }
            if (157.5 <= rotation && rotation < 202.5) {
                return SOUTH
            }
            if (202.5 <= rotation && rotation < 247.5) {
                return SOUTH_WEST
            }
            if (247.5 <= rotation && rotation < 292.5) {
                return WEST
            }
            if (292.5 <= rotation && rotation < 337.5) {
                return NORTH_WEST
            }
            return if (337.5 <= rotation && rotation < 360.0) {
                NORTH
            } else null
        }
    }
}