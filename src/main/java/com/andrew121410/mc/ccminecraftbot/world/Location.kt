package com.andrew121410.mc.ccminecraftbot.world

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position

data class Location(var x: Double, var y: Double, var z: Double, var yaw: Float = 0f, var pitch: Float = 0f) : Comparable<Location> {

    fun isZero() = x == 0.0 && y == 0.0 && z == 0.0

    fun isYawPitchZero(): Boolean {
        return yaw == 0f && pitch == 0f
    }

    fun add(x: Double, y: Double, z: Double, yaw: Float = 0f, pitch: Float = 0f): Location {
        this.x += x
        this.y += y
        this.z += z
        this.yaw += yaw
        this.pitch += pitch
        return this
    }

    fun add(location: Location): Location {
        this.x += location.x
        this.y += location.y
        this.z += location.z
        this.yaw += location.yaw
        this.pitch += location.pitch
        return this
    }

    fun subtract(x: Double, y: Double, z: Double, yaw: Float = 0f, pitch: Float = 0f): Location {
        this.x -= x
        this.y -= y
        this.z -= z
        this.yaw -= yaw
        this.pitch -= pitch
        return this
    }

    val block: Int
        get() = CCBotMinecraft.getInstance().player.chunkCache.getBlockID(toPosition())

    fun toPosition(): Position {
        return Position(x.toInt(), y.toInt(), z.toInt())
    }

    fun distanceSquared(to: Location): Double {
        return square(x - to.x) + square(
            y - to.y
        ) + square(z - to.z)
    }

    override operator fun compareTo(other: Position): Int {
        val distance = PositionDelta.from(Position(), this)
        val distanceOther = PositionDelta.from(Position(), other)
        return when {
            distance > distanceOther -> 1
            distance < distanceOther -> -1
            else -> 0
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Position

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false
        if (modifying != other.modifying) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + modifying.hashCode()
        return result
    }

    companion object {
        fun from(position: Position): Location {
            return Location(position.x.toDouble(), position.y.toDouble(), position.z.toDouble())
        }

        fun square(num: Double): Double {
            return num * num
        }
    }
}