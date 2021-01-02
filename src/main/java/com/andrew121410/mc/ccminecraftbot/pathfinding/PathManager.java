package com.andrew121410.mc.ccminecraftbot.pathfinding;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.world.Location;
import lombok.ToString;

@ToString
public class PathManager {

    private CCBotMinecraft CCBotMinecraft;
    private CCPlayer ccPlayer;

    public PathManager(CCBotMinecraft CCBotMinecraft, CCPlayer ccPlayer) {
        this.CCBotMinecraft = CCBotMinecraft;
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
