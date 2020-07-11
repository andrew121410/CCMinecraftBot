package com.andrew121410.mc.ccminecraftbot.player.inventory;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.utils.BlocksAndItems;
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

    private Main main;
    private CCPlayer ccPlayer;

    @Getter
    private Integer heldItemSlot;

    @Getter
    private ItemStack cursor;

    @Getter
    private int actionId = 0;

    public PlayerInventory(Main main, CCPlayer ccPlayer) {
        this.main = main;
        this.ccPlayer = ccPlayer;
        this.itemStackMap = new HashMap<>();
    }

    public void handleServerWindowItemsPacket(ServerWindowItemsPacket packet) {
        if (packet.getWindowId() == 0) {
            for (int i = 0; i < packet.getItems().length; i++) {
                ItemStack itemStack = packet.getItems()[i];
                if (itemStack == null) continue;
                this.itemStackMap.remove(i);
                this.itemStackMap.put(i, new InventorySlot(i, toSlot(i), itemStack));
            }
        }
    }

    public void handleServerSetSlotPacket(ServerSetSlotPacket packet) {
        //Cursor
        if (packet.getWindowId() == 255 && packet.getSlot() == -1) {
            this.cursor = packet.getItem();
            return;
        } else if (packet.getWindowId() == 0) {
            this.itemStackMap.remove(packet.getSlot());
            if (packet.getItem() == null) return;
            this.itemStackMap.put(packet.getSlot(), new InventorySlot(packet.getSlot(), toSlot(packet.getSlot()), packet.getItem()));
            return;
        }
    }

    public void handleServerPlayerChangeHeldItemPacket(ServerPlayerChangeHeldItemPacket packet) {
        this.heldItemSlot = packet.getSlot();
    }

    public void moveCursor(int to) {
        ClientPlayerChangeHeldItemPacket packet = new ClientPlayerChangeHeldItemPacket(toSlot(to));
        this.main.getClient().getSession().send(packet);
        this.heldItemSlot = to;
        if (this.itemStackMap.containsKey(to))
            this.cursor = this.itemStackMap.get(to).getItemStack();
        else this.cursor = null;
    }

    public InventoryMessage moveSlotToCursor(int slot) {
        if (isHotbarSlot(slot)) {
            moveCursor(slot);
            return InventoryMessage.SUCCESS;
        }
        InventorySlot fromSlot = this.getItemStackMap().get(slot);
        if (fromSlot == null) return InventoryMessage.CANT_BECAUSE_NO_ITEM;
        ItemStack fromItem = fromSlot.getItemStack();
        if (this.cursor == null) this.getItemStackMap().remove(slot);
        else fromSlot.setItemStack(this.cursor);
        this.cursor = fromItem;
        ClientMoveItemToHotbarPacket packet = new ClientMoveItemToHotbarPacket(slot);
        this.main.getClient().getSession().send(packet);
        closeWindow(0);
        return InventoryMessage.SUCCESS;
    }

    public InventoryMessage dropFullStack(int slot) {
        InventorySlot inventorySlot = this.itemStackMap.get(slot);
        if (inventorySlot == null) return InventoryMessage.CANT_BECAUSE_NO_ITEM;
        this.actionId++;
        ClientWindowActionPacket packet = new ClientWindowActionPacket(0, this.actionId, inventorySlot.getRawSlot(), inventorySlot.getItemStack(), WindowAction.DROP_ITEM, DropItemParam.DROP_SELECTED_STACK);
        this.main.getClient().getSession().send(packet);
        closeWindow(0);
        return InventoryMessage.SUCCESS;
    }

    public void closeWindow(int windowId) {
        ClientCloseWindowPacket packet = new ClientCloseWindowPacket(windowId);
        this.main.getClient().getSession().send(packet);
    }

    public InventorySlot findFood() {
        return this.itemStackMap.values().stream().filter(inventorySlot -> BlocksAndItems.isFood(inventorySlot.getItemStack().getId())).findFirst().orElse(null);
    }

    public void findFoodAndEatIt() {
        InventorySlot inventorySlot = findFood();
        if (inventorySlot == null) return;
        moveSlotToCursor(inventorySlot.getSlot());
        ClientPlayerUseItemPacket packet = new ClientPlayerUseItemPacket(Hand.MAIN_HAND);
        this.main.getClient().getSession().send(packet);
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

    public boolean isHotbarSlot(int unknownSlot) {
        return unknownSlot >= 36 && unknownSlot <= 44;
    }
}
