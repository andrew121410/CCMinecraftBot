package com.andrew121410.mc.ccminecraftbot.player;

import com.andrew121410.mc.ccminecraftbot.Main;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class PlayerMoveHandler {

    private Main main;
    private CCPlayer ccPlayer;

    public PlayerMoveHandler(Main main, CCPlayer ccPlayer) {
        this.main = main;
        this.ccPlayer = ccPlayer;
    }

    public void goForward() {
    }
}
