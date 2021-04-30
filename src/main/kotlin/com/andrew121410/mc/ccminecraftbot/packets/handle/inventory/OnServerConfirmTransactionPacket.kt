package com.andrew121410.mc.ccminecraftbot.packets.handle.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket;
import lombok.AllArgsConstructor;

public class OnServerConfirmTransactionPacket extends PacketHandler<ServerConfirmTransactionPacket> {

    @Override
    public void handle(ServerConfirmTransactionPacket packet, CCBotMinecraft ccBotMinecraft) {
        if (!packet.isAccepted()) {
            ClientConfirmTransactionPacket confirmPacket = new ClientConfirmTransactionPacket(packet.getWindowId(), packet.getActionId(), true);
            ccBotMinecraft.getClient().getSession().send(confirmPacket);
        }
    }
}
