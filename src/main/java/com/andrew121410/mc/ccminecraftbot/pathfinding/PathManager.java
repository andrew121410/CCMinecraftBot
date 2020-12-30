package com.andrew121410.mc.ccminecraftbot.pathfinding;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.world.Location;
import lombok.ToString;

@ToString
public class PathManager {

    private Main main;
    private CCPlayer ccPlayer;

    public PathManager(Main main, CCPlayer ccPlayer) {
        this.main = main;
        this.ccPlayer = ccPlayer;
    }

    public void goToLocation(Location to) {
        Location from = ccPlayer.getLocation();
        AStar aStar = new AStar(from, to, 1000, false, 1);
        Location[] locations = aStar.getPath();
        for (Location location : locations) {
            ccPlayer.setLocation(location);
            ccPlayer.sendPlayerPositionRotationPacket();
            ccPlayer.sendPlayerMovementPacket();
        }
    }
}
