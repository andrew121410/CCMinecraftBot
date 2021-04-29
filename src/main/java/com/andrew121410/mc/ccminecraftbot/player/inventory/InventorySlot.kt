package com.andrew121410.mc.ccminecraftbot.player.inventory

import com.andrew121410.mc.ccminecraftbot.objects.Item
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack

data class InventorySlot(
    val slot: Int,
    var itemStack: ItemStack,
    var item: Item
)