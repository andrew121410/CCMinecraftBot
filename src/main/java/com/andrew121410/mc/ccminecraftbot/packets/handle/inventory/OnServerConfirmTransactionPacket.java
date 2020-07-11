package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerConfirmTransactionPacket extends PacketHandler<ServerConfirmTransactionPacket> {

    private Main main;

    @Override
    public void handle(ServerConfirmTransactionPacket packet) {
        if (!packet.isAccepted()) {
            ClientConfirmTransactionPacket confirmPacket = new ClientConfirmTransactionPacket(packet.getWindowId(), packet.getActionId(), true);
            main.getClient().getSession().send(confirmPacket);
        }
    }
}
