package com.andrew121410.mc.ccminecraftbot.player.inventory;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InventorySlot {

    private int rawSlot;
    private int slot;
    private ItemStack itemStack;

    public InventorySlot(int rawSlot, int slot, ItemStack itemStack) {
        this.rawSlot = rawSlot;
        this.slot = slot;
        this.itemStack = itemStack;
    }
}
