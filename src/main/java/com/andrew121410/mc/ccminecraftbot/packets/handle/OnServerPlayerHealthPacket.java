package com.andrew121410.mc.ccminecraftbot.packets.handle;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler;
import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnServerPlayerHealthPacket extends PacketHandler<ServerPlayerHealthPacket> {

    private Main main;

    @Override
    public void handle(ServerPlayerHealthPacket packet) {
        if (packet.getHealth() == 0.0) {
            //Make the bot respawn on it's own.
            ClientRequestPacket clientRequestPacket = new ClientRequestPacket(ClientRequest.RESPAWN);
            this.main.getClient().getSession().send(clientRequestPacket);
            System.out.println("Bot has respawn.");
        } else if (packet.getFood() < 20) {

        }
    }
}
