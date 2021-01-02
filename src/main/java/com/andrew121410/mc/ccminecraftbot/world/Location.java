package com.andrew121410.mc.ccminecraftbot.world;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Location {

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Location subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Integer getBlock() {
        return CCBotMinecraft.getInstance().getPlayer().getChunkCache().getBlockAt(toPosition());
    }

    public Position toPosition() {
        return new Position((int) x, (int) y, (int) z);
    }

    public double distanceSquared(Location to) {
        return square(x - to.x) + square(y - to.y) + square(z - to.z);
    }

    public Location clone() {
        return new Location(x, y, z, yaw, pitch);
    }

    public static double square(double num) {
        return num * num;
    }
}
