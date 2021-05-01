package com.andrew121410.mc.ccminecraftbot.commands

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer
import com.andrew121410.mc.ccminecraftbot.world.Location
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket
import java.util.*

class CommandManager(private val ccBotMinecraft: CCBotMinecraft) {
    fun onChat(sender: String, fullCommand: String) {
        val ccPlayer: CCPlayer = ccBotMinecraft.player

        if (!sender.equals("[Owner]", ignoreCase = true)) {
            sendMessage("Andrew is not that dumb silly lol.")
            return
        }
        var args = fullCommand.split(" ".toRegex()).toTypedArray()
        val command = args[0]
        args = Arrays.copyOfRange(args, 1, args.size)
        if (command.equals("inv_move_cursor", ignoreCase = true) && args.size == 1) {
            ccPlayer.playerInventory.moveSlotToCursor(args[0].toInt())
            sendMessage("Cursor has been set.")
            return
        } else if (command.equals("inv_find_food", ignoreCase = true)) {
            val inventorySlot = ccPlayer.playerInventory.findFood()
            if (inventorySlot == null) {
                sendMessage("Couldn't find food.")
                return
            }
            sendMessage("Food was found: " + inventorySlot.item)
            return
        } else if (command.equals("inv_find_food_and_eat_it", ignoreCase = true)) {
            ccPlayer.playerInventory.findFoodAndEatIt()
            sendMessage("Trying.")
        } else if (command.equals("inv_drop_all", ignoreCase = true) && args.size == 1) {
            ccPlayer.playerInventory.dropFullStack(args[0].toInt())
            sendMessage("Dropping full stack in slot: " + args[0])
        } else if (command.equals("path_go_to", ignoreCase = true) && args.size == 3) {
            val x = args[0].toInt()
            val y = args[1].toInt()
            val z = args[2].toInt()
            val location = Location(
                x.toDouble(), y.toDouble(), z.toDouble(), 0f, 0f
            )
            ccPlayer.movementManager!!.moveTo(location, 4.4)
            sendMessage("Going to path hopefully.")
        } else if (command.equals("run_cmd", ignoreCase = true)) {
            val commandToSend = java.lang.String.join(" ", *args)
            sendMessage("/$commandToSend")
        } else if (command.equals("quit", ignoreCase = true)) {
            sendMessage("Quitting")
            ccBotMinecraft.quit()
        } else if (command.equals("ping", ignoreCase = true)) {
            sendMessage("Hello I heard you.")
            return
        }
    }

    companion object {
        fun sendMessage(message: String) {
            val clientChatPacket = ClientChatPacket(message)
            CCBotMinecraft.client.session.send(clientChatPacket)
        }
    }
}