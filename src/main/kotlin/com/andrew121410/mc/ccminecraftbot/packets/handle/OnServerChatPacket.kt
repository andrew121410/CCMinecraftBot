package com.andrew121410.mc.ccminecraftbot.packets.handle

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.commands.CommandManager
import com.andrew121410.mc.ccminecraftbot.packets.PacketHandler
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import java.util.function.Consumer

class OnServerChatPacket : PacketHandler<ServerChatPacket>() {
    override fun handle(packet: ServerChatPacket, ccBotMinecraft: CCBotMinecraft) {
        val ccPlayer = ccBotMinecraft.player
        if (packet.message is TextComponent) {
            val textComponent = packet.message as TextComponent
            val stringBuilder = StringBuilder()
            textComponent.children()
                .forEach(Consumer { message: Component -> stringBuilder.append((message as TextComponent).content()) })
            val complete = stringBuilder.toString()
            if (!complete.contains("ccbot")) return
            val args = complete.split(" ".toRegex()).toTypedArray()
            args[0] = args[0].replace(":".toRegex(), "")
            val sender = args[0]
            val command: String
            command = try {
                complete.substring(complete.lastIndexOf("ccbot") + 6)
            } catch (exception: IndexOutOfBoundsException) {
                CommandManager.Companion.sendMessage("IndexOutOfBoundsException was thrown. Something went wrong!")
                return
            }
            println("COMMAND: $command")
            ccBotMinecraft.commandManager.onChat(sender, command)
        }
    }
}