package com.andrew121410.mc.ccminecraftbot.player.inventory;

import com.andrew121410.mc.ccminecraftbot.Main;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.andrew121410.mc.ccminecraftbot.utils.Food;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientMoveItemToHotbarPacket;
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
                this.itemStackMap.putIfAbsent(i, new InventorySlot(i, itemStack));
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
            this.itemStackMap.put(packet.getSlot(), new InventorySlot(packet.getSlot(), packet.getItem()));
            return;
        }
    }

    public void handleServerPlayerChangeHeldItemPacket(ServerPlayerChangeHeldItemPacket packet) {
        this.heldItemSlot = packet.getSlot();
    }

    public InventorySlot findFood() {
        return this.itemStackMap.values().stream().filter(inventorySlot -> Food.isFood(inventorySlot.getItemStack().getId())).findFirst().orElse(null);
    }

    public void moveCursor(int to) {
        ClientPlayerChangeHeldItemPacket packet = new ClientPlayerChangeHeldItemPacket(to);
        this.main.getClient().getSession().send(packet);
        this.heldItemSlot = to;
        if (this.itemStackMap.containsKey(to))
            this.cursor = this.itemStackMap.get(to).getItemStack();
        else this.cursor = null;
    }

    public InventoryMessage moveSlotToCursor(int slot) {
        if (isHotbarSlot(slot)) {
            moveCursor(toHotbarSlotNumber(slot));
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
        return InventoryMessage.SUCCESS;
    }

    public void findFoodAndEatIt() {
        InventorySlot inventorySlot = findFood();
        if (inventorySlot == null) return;
        moveSlotToCursor(inventorySlot.getSlot());
        ClientPlayerUseItemPacket packet = new ClientPlayerUseItemPacket(Hand.MAIN_HAND);
        this.main.getClient().getSession().send(packet);
    }

    public Integer toHotbarSlotNumber(int slot) {
        switch (slot) {
            case 36:
                return 0;
            case 37:
                return 1;
            case 38:
                return 2;
            case 39:
                return 3;
            case 40:
                return 4;
            case 41:
                return 5;
            case 42:
                return 6;
            case 43:
                return 7;
            case 44:
                return 8;
            default:
                return null;
        }
    }

    public boolean isHotbarSlot(int slot) {
        return toHotbarSlotNumber(slot) != null;
    }
}
