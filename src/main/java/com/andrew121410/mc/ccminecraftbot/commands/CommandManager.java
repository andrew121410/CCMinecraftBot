package com.andrew121410.mc.ccminecraftbot.commands;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.player.inventory.InventorySlot;
import com.andrew121410.mc.ccminecraftbot.world.Location;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class CommandManager {

    private CCBotMinecraft ccBotMinecraft;

    public void onChat(String sender, String fullCommand) {
        if (!sender.equalsIgnoreCase("[Owner]")) {
            sendMessage("Andrew is not that dumb silly lol.");
            return;
        }
        String[] args = fullCommand.split(" ");
        String command = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        if (command.equalsIgnoreCase("inv_move_cursor") && args.length == 1) {
            this.ccBotMinecraft.getPlayer().getPlayerInventory().moveCursor(Integer.parseInt(args[0]));
            sendMessage("Cursor has been set to: " + args[0]);
            return;
        } else if (command.equalsIgnoreCase("inv_find_food")) {
            InventorySlot inventorySlot = this.ccBotMinecraft.getPlayer().getPlayerInventory().findFood();
            if (inventorySlot == null) {
                sendMessage("Couldn't find food.");
                return;
            }
            sendMessage("Food was found: " + inventorySlot.toString());
            return;
        } else if (command.equalsIgnoreCase("inv_find_food_and_eat_it")) {
            this.ccBotMinecraft.getPlayer().getPlayerInventory().findFoodAndEatIt();
            sendMessage("Trying.");
        } else if (command.equalsIgnoreCase("inv_drop_all") && args.length == 1) {
            this.ccBotMinecraft.getPlayer().getPlayerInventory().dropFullStack(Integer.parseInt(args[0]));
            sendMessage("Dropping full stack in slot: " + args[0]);
        } else if (command.equalsIgnoreCase("path_go_a") && args.length == 3) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            Location location = new Location(x, y, z, 0F, 0F);
            this.ccBotMinecraft.getPlayer().movementManager.moveTo(location, 4.4);
            sendMessage("Going to path hopefully.");
        } else if (command.equalsIgnoreCase("run_cmd")) {
            String commandToSend = String.join(" ", args);
            sendMessage("/" + commandToSend);
        } else if (command.equalsIgnoreCase("quit")) {
            sendMessage("Quitting");
            this.ccBotMinecraft.quit();
        } else if (command.equalsIgnoreCase("ping")) {
            sendMessage("Hello I heard you.");
            return;
        }
    }

    public static void sendMessage(String message) {
        ClientChatPacket clientChatPacket = new ClientChatPacket(message);
        CCBotMinecraft.getInstance().getClient().getSession().send(clientChatPacket);
    }
}