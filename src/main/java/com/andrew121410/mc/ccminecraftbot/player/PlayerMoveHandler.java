package com.andrew121410.mc.ccminecraftbot.player;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class PlayerMoveHandler {

    private CCBotMinecraft CCBotMinecraft;
    private CCPlayer ccPlayer;

    public PlayerMoveHandler(CCBotMinecraft CCBotMinecraft, CCPlayer ccPlayer) {
        this.CCBotMinecraft = CCBotMinecraft;
        this.ccPlayer = ccPlayer;
    }

    public void goForward() {
    }
}
