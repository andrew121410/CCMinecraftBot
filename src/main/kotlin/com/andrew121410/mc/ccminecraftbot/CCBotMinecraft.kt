package com.andrew121410.mc.ccminecraftbot

import com.andrew121410.mc.ccminecraftbot.commands.CommandManager
import com.andrew121410.mc.ccminecraftbot.config.CCMinecraftBotJacksonConfig
import com.andrew121410.mc.ccminecraftbot.config.ConfigUtils
import com.andrew121410.mc.ccminecraftbot.packets.PacketSessionAdapter
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer
import com.andrew121410.mc.ccminecraftbot.utils.ResourceManager.load
import com.github.steveice10.mc.auth.exception.request.RequestException
import com.github.steveice10.mc.auth.service.AuthenticationService
import com.github.steveice10.mc.protocol.MinecraftProtocol
import com.github.steveice10.packetlib.tcp.TcpClientSession
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.system.exitProcess

object CCBotMinecraft {

    @JvmStatic
    fun main(args: Array<String>) {

    }

    var isShuttingDown = false

    private val config: CCMinecraftBotJacksonConfig =
        ConfigUtils.loadConfig() ?: throw NullPointerException("Please update the config.yml file")

    lateinit var client: TcpClientSession
    lateinit var commandManager: CommandManager
    lateinit var player: CCPlayer

    init {
        setupMinecraftBot()
        setupScanner()
    }

    fun setupMinecraftBot() {
        load() //Loads up resources (blocks/items)
        val protocol: MinecraftProtocol
        try {
            val authenticationService: AuthenticationService = AuthenticationService()
            authenticationService.username = config.minecraftUsername
            authenticationService.password = config.minecraftPassword
            authenticationService.login()
            protocol = MinecraftProtocol(authenticationService.selectedProfile, authenticationService.accessToken)
            println("Successfully authenticated user.")
        } catch (e: RequestException) {
            e.printStackTrace()
            exitProcess(1)
        }
        client = TcpClientSession(config.serverHost, config.serverPort.toInt(), protocol)
        client.addListener(PacketSessionAdapter(this))
        client.connect()
        commandManager = CommandManager(this)
    }

    private fun setupScanner() {
        Thread({
            val bufferedReader = BufferedReader(InputStreamReader(System.`in`))
            var line: String?
            try {
                while (bufferedReader.readLine().also { line = it } != null) {
                    when (line) {
                        "end", "stop", "exit", "quit" -> {
                            quit()
                            println("Not a command?")
                        }
                        else -> println("Not a command?")
                    }
                }
            } catch (ignored: IOException) {
            }
        }, "MyDiscordSocketBot-Scanner").start()
    }

    fun quit() {
        isShuttingDown = true
        println("Shutting down.")
        client.disconnect("Shutting down!")
        exitProcess(1)
    }
}