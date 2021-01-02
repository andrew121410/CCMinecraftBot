package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerCloseWindowPacket extends PacketHandler<ServerCloseWindowPacket> {

    private CCBotMinecraft CCBotMinecraft;

    @Override
    public void handle(ServerCloseWindowPacket packet) {
        this.CCBotMinecraft.getPlayer().getPlayerInventory().closeWindow(packet.getWindowId());
    }
}
