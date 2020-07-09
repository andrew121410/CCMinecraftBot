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

    private ItemStack itemStack;
    private int slot;

    public InventorySlot(int slot, ItemStack itemStack) {
        this.itemStack = itemStack;
        this.slot = slot;
    }
}
