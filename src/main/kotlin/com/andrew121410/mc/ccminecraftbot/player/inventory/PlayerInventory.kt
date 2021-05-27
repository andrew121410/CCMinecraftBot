package com.andrew121410.mc.ccminecraftbot.player.inventory

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.commands.CommandManager
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer
import com.andrew121410.mc.ccminecraftbot.utils.Blocks
import com.andrew121410.mc.ccminecraftbot.utils.ResourceManager.items
import com.github.steveice10.mc.protocol.data.game.entity.player.Hand
import com.github.steveice10.mc.protocol.data.game.window.DropItemParam
import com.github.steveice10.mc.protocol.data.game.window.WindowAction
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientCloseWindowPacket
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientMoveItemToHotbarPacket
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket


class PlayerInventory(private val CCBotMinecraft: CCBotMinecraft, private val ccPlayer: CCPlayer) {
    /**
     * ClientPlayerChangeHeldItemPacket -> Used when you change to another slot for hotbar.
     * ClientMoveItemToHotbarPacket -> Used to switch item from inv;
     */

    private val itemStackMap: MutableMap<Int, InventorySlot?> = HashMap()

    private var heldItemSlot: Int? = null

    private var cursor: InventorySlot? = null

    private var actionId = 0

    fun handleServerWindowItemsPacket(packet: ServerWindowItemsPacket) {
        if (packet.windowId == 0) {
            for (i in packet.items.indices) {
                val itemStack = packet.items[i] ?: continue //Do not remove Elvis Op
                itemStackMap.remove(i)
                itemStackMap[i] = InventorySlot(
                    i,
                    itemStack,
                    items[itemStack.id]!!
                )
            }
        }
    }

    fun handleServerSetSlotPacket(packet: ServerSetSlotPacket) {
        // Set's the Cursor
        if (packet.windowId == 255 && packet.slot == -1) {
            cursor = itemStackMap[packet.slot]
        } else if (packet.windowId == 0) {
            // Update the player inventory with the new item
            itemStackMap.remove(packet.slot)
            if (packet.item == null) return
            itemStackMap[packet.slot] = InventorySlot(
                packet.slot,
                packet.item,
                items[packet.item.id]!!
            )
        }
    }

    fun handleServerPlayerChangeHeldItemPacket(packet: ServerPlayerChangeHeldItemPacket) {
        heldItemSlot = packet.slot
    }

    fun moveCursor(slot: Int) {
        if (!isHotbarSlot(slot)) {
            CommandManager.sendMessage("That's not a valid slot.")
            return
        }
        // The slot which the player has selected (0–8) not (36-44)
        val packet = ClientPlayerChangeHeldItemPacket(toSlot(slot))
        CCBotMinecraft.client.send(packet)
        heldItemSlot = slot
        cursor = itemStackMap.getOrDefault(slot, null)
    }

    fun moveSlotToCursor(fromSlotNumber: Int): InventoryMessage {
        if (isHotbarSlot(fromSlotNumber)) {
            moveCursor(fromSlotNumber)
            return InventoryMessage.SUCCESS
        }
        val (_, itemStack, item) = itemStackMap.getOrDefault(fromSlotNumber, null)
            ?: return InventoryMessage.CANT_BECAUSE_NO_ITEM
        if (cursor == null) {
            itemStackMap.remove(fromSlotNumber)
        } else {
            cursor!!.itemStack = itemStack
            cursor!!.item = item
        }
        val packet = ClientMoveItemToHotbarPacket(fromSlotNumber)
        CCBotMinecraft.client.send(packet)
        closeWindow(0)
        return InventoryMessage.SUCCESS
    }

    fun dropFullStack(slot: Int): InventoryMessage {
        val (slot1, itemStack) = itemStackMap[slot] ?: return InventoryMessage.CANT_BECAUSE_NO_ITEM
        actionId++
        val packet = ClientWindowActionPacket(
            0,
            actionId,
            slot1,
            itemStack,
            WindowAction.DROP_ITEM,
            DropItemParam.DROP_SELECTED_STACK
        )
        CCBotMinecraft.client.send(packet)
        closeWindow(0)
        return InventoryMessage.SUCCESS
    }

    fun closeWindow(windowId: Int) {
        val packet = ClientCloseWindowPacket(windowId)
        CCBotMinecraft.client.send(packet)
    }

    fun findFood(): InventorySlot? {
        return itemStackMap.values.stream().filter { inventorySlot: InventorySlot? ->
            Blocks.isFood(
                inventorySlot!!.itemStack.id
            )
        }.findFirst().orElse(null)
    }

    fun findFoodAndEatIt() {
        val inventorySlot = findFood()
        if (inventorySlot == null) {
            CommandManager.sendMessage("findFoodAndEatIt couldn't find food.")
            return
        }
        moveSlotToCursor(inventorySlot.slot)
        val packet = ClientPlayerUseItemPacket(Hand.MAIN_HAND)
        CCBotMinecraft.client.send(packet)
    }

    fun isHotbarSlot(unknownSlot: Int): Boolean {
        return unknownSlot >= 36 && unknownSlot <= 44
    }

    fun toSlot(rawSlot: Int): Int {
        //HotBar
        if (rawSlot >= 36 && rawSlot <= 44) {
            return rawSlot - 36
            //Inventory
        } else if (rawSlot >= 9 && rawSlot <= 35) {
            return rawSlot
            //Armour
        } else if (rawSlot >= 5 && rawSlot <= 8) {
            when (rawSlot) {
                5 -> return 39
                6 -> return 38
                7 -> return 37
                8 -> return 36
            }
            //Offhand.
        } else if (rawSlot == 45) {
            return 40
            //UI
        } else if (rawSlot >= 1 && rawSlot <= 4 || rawSlot == 0) {
            return rawSlot
        }
        return -1214
    }
}