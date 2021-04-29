package com.andrew121410.mc.ccminecraftbot.player.inventory;

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft;
import com.andrew121410.mc.ccminecraftbot.commands.CommandManager;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.utils.Blocks;
import com.andrew121410.mc.ccminecraftbot.utils.ResourceManager;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.data.game.window.DropItemParam;
import com.github.steveice10.mc.protocol.data.game.window.WindowAction;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientCloseWindowPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientMoveItemToHotbarPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
public class PlayerInventory {

    /**
     * ClientPlayerChangeHeldItemPacket -> Used when you change to another slot for hotbar.
     * ClientMoveItemToHotbarPacket -> Used to switch item from inv;
     */

    @Getter
    private Map<Integer, InventorySlot> itemStackMap;

    private CCBotMinecraft CCBotMinecraft;
    private CCPlayer ccPlayer;

    @Getter
    private Integer heldItemSlot;

    @Getter
    private InventorySlot cursor;

    @Getter
    private int actionId = 0;

    public PlayerInventory(CCBotMinecraft CCBotMinecraft, CCPlayer ccPlayer) {
        this.CCBotMinecraft = CCBotMinecraft;
        this.ccPlayer = ccPlayer;
        this.itemStackMap = new HashMap<>();
    }

    public void handleServerWindowItemsPacket(ServerWindowItemsPacket packet) {
        if (packet.getWindowId() == 0) {
            for (int i = 0; i < packet.getItems().length; i++) {
                ItemStack itemStack = packet.getItems()[i];
                if (itemStack == null) continue;
                this.itemStackMap.remove(i);
                this.itemStackMap.put(i, new InventorySlot(i, itemStack, ResourceManager.INSTANCE.getItems().get(itemStack.getId())));
            }
        }
    }

    public void handleServerSetSlotPacket(ServerSetSlotPacket packet) {
        // Set's the Cursor
        if (packet.getWindowId() == 255 && packet.getSlot() == -1) {
            this.cursor = this.itemStackMap.get(packet.getSlot());
        } else if (packet.getWindowId() == 0) {
            // Update the player inventory with the new item
            this.itemStackMap.remove(packet.getSlot());
            if (packet.getItem() == null) return;
            this.itemStackMap.put(packet.getSlot(), new InventorySlot(packet.getSlot(), packet.getItem(), ResourceManager.INSTANCE.getItems().get(packet.getItem().getId())));
        }
    }

    public void handleServerPlayerChangeHeldItemPacket(ServerPlayerChangeHeldItemPacket packet) {
        this.heldItemSlot = packet.getSlot();
    }

    public void moveCursor(int slot) {
        if (!isHotbarSlot(slot)) {
            CommandManager.sendMessage("That's not a valid slot.");
            return;
        }
        // The slot which the player has selected (0–8) not (36-44)
        ClientPlayerChangeHeldItemPacket packet = new ClientPlayerChangeHeldItemPacket(toSlot(slot));
        this.CCBotMinecraft.getClient().getSession().send(packet);
        this.heldItemSlot = slot;
        this.cursor = this.itemStackMap.getOrDefault(slot, null);
    }

    public InventoryMessage moveSlotToCursor(int fromSlotNumber) {
        if (isHotbarSlot(fromSlotNumber)) {
            moveCursor(fromSlotNumber);
            return InventoryMessage.SUCCESS;
        }
        InventorySlot fromSlot = this.itemStackMap.getOrDefault(fromSlotNumber, null);
        if (fromSlot == null) return InventoryMessage.CANT_BECAUSE_NO_ITEM;
        if (this.cursor == null) {
            this.itemStackMap.remove(fromSlotNumber);
        } else {
            this.cursor.setItemStack(fromSlot.getItemStack());
            this.cursor.setItem(fromSlot.getItem());
        }
        ClientMoveItemToHotbarPacket packet = new ClientMoveItemToHotbarPacket(fromSlotNumber);
        this.CCBotMinecraft.getClient().getSession().send(packet);
        closeWindow(0);
        return InventoryMessage.SUCCESS;
    }

    public InventoryMessage dropFullStack(int slot) {
        InventorySlot inventorySlot = this.itemStackMap.get(slot);
        if (inventorySlot == null) return InventoryMessage.CANT_BECAUSE_NO_ITEM;
        this.actionId++;
        ClientWindowActionPacket packet = new ClientWindowActionPacket(0, this.actionId, inventorySlot.getSlot(), inventorySlot.getItemStack(), WindowAction.DROP_ITEM, DropItemParam.DROP_SELECTED_STACK);
        this.CCBotMinecraft.getClient().getSession().send(packet);
        closeWindow(0);
        return InventoryMessage.SUCCESS;
    }

    public void closeWindow(int windowId) {
        ClientCloseWindowPacket packet = new ClientCloseWindowPacket(windowId);
        this.CCBotMinecraft.getClient().getSession().send(packet);
    }

    public InventorySlot findFood() {
        return this.itemStackMap.values().stream().filter(inventorySlot -> Blocks.isFood(inventorySlot.getItemStack().getId())).findFirst().orElse(null);
    }

    public void findFoodAndEatIt() {
        InventorySlot inventorySlot = findFood();
        if (inventorySlot == null) {
            CommandManager.sendMessage("findFoodAndEatIt couldn't find food.");
            return;
        }
        moveSlotToCursor(inventorySlot.getSlot());
        ClientPlayerUseItemPacket packet = new ClientPlayerUseItemPacket(Hand.MAIN_HAND);
        this.CCBotMinecraft.getClient().getSession().send(packet);
    }

    public boolean isHotbarSlot(int unknownSlot) {
        return unknownSlot >= 36 && unknownSlot <= 44;
    }

    public int toSlot(int rawSlot) {
        //HotBar
        if (rawSlot >= 36 && rawSlot <= 44) {
            return rawSlot - 36;
            //Inventory
        } else if (rawSlot >= 9 && rawSlot <= 35) {
            return rawSlot;
            //Armour
        } else if (rawSlot >= 5 && rawSlot <= 8) {
            switch (rawSlot) {
                case 5:
                    return 39;
                case 6:
                    return 38;
                case 7:
                    return 37;
                case 8:
                    return 36;
            }
            //Offhand.
        } else if (rawSlot == 45) {
            return 40;
            //UI
        } else if (rawSlot >= 1 && rawSlot <= 4 || rawSlot == 0) {
            return rawSlot;
        }
        return -1214;
    }
}
