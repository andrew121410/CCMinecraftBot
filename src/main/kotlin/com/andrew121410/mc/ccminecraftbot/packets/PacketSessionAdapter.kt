package com.andrew121410.mc.ccminecraftbot.packets

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.packets.handle.*
import com.andrew121410.mc.ccminecraftbot.packets.handle.chunk.OnServerBlockChangePacket
import com.andrew121410.mc.ccminecraftbot.packets.handle.chunk.OnServerChunkDataPacket
import com.andrew121410.mc.ccminecraftbot.packets.handle.chunk.OnServerMultiBlockChangePacket
import com.andrew121410.mc.ccminecraftbot.packets.handle.chunk.OnServerUnloadChunkPacket
import com.andrew121410.mc.ccminecraftbot.packets.handle.inventory.*
import com.andrew121410.mc.ccminecraftbot.packets.handle.login.OnLoginSuccessPacket
import com.andrew121410.mc.ccminecraftbot.packets.handle.login.OnServerJoinGamePacket
import com.andrew121410.mc.ccminecraftbot.packets.handle.position.*
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerKeepAlivePacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityRotationPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerChangeHeldItemPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerConfirmTransactionPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket
import com.github.steveice10.mc.protocol.packet.ingame.server.world.*
import com.github.steveice10.mc.protocol.packet.login.server.LoginSuccessPacket
import com.github.steveice10.packetlib.event.session.*
import com.github.steveice10.packetlib.packet.Packet
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class PacketSessionAdapter(private val CCBotMinecraft: CCBotMinecraft) : SessionAdapter() {
    private val packets: MutableMap<Class<out Packet>, PacketHandler<out Packet>> = HashMap()

    override fun packetReceived(event: PacketReceivedEvent) {
        if (event.getPacket<Packet>() is ServerUpdateLightPacket || event.getPacket<Packet>() is ServerKeepAlivePacket || event.getPacket<Packet>() is ServerUpdateTimePacket) {
            return  //Not needed
        }
        //Handles the packets.
        if (packets.containsKey(event.getPacket<Packet>().javaClass)) {
            packets[event.getPacket<Packet>().javaClass]!!
                .handle(event.getPacket(), CCBotMinecraft)
        }
    }

    override fun packetSending(event: PacketSendingEvent) {}
    override fun packetSent(event: PacketSentEvent) {}
    override fun packetError(event: PacketErrorEvent) {}
    override fun connected(event: ConnectedEvent) {}
    override fun disconnecting(event: DisconnectingEvent) {}

    @Throws
    override fun disconnected(event: DisconnectedEvent) {
        println("Disconnected: " + event.reason)
        if (event.cause != null) {
            event.cause.printStackTrace()
            return
        }
        if (event.reason.contains("ban")) {
            return
        }
        if (!CCBotMinecraft.isShuttingDown) {
            val executorService = Executors.newSingleThreadScheduledExecutor()
            executorService.scheduleAtFixedRate({
                println("Trying to reconnect.")
                CCBotMinecraft.setupMinecraftBot()
                executorService.shutdown()
            }, 1, 2, TimeUnit.MINUTES)
        }
    }

    private fun registerPackets() {
        //Login
        packets[LoginSuccessPacket::class.java] = OnLoginSuccessPacket()
        packets[ServerJoinGamePacket::class.java] = OnServerJoinGamePacket()
        packets[ServerPlayerHealthPacket::class.java] = OnServerPlayerHealthPacket()
        packets[ServerChatPacket::class.java] = OnServerChatPacket()

        //Chunks
        packets[ServerChunkDataPacket::class.java] = OnServerChunkDataPacket()
        packets[ServerUnloadChunkPacket::class.java] = OnServerUnloadChunkPacket()
        packets[ServerBlockChangePacket::class.java] = OnServerBlockChangePacket()
        packets[ServerMultiBlockChangePacket::class.java] = OnServerMultiBlockChangePacket()

        //Inv
        packets[ServerPlayerChangeHeldItemPacket::class.java] = OnServerPlayerChangeHeldItemPacket()
        packets[ServerSetSlotPacket::class.java] = OnServerSetSlotPacket()
        packets[ServerWindowItemsPacket::class.java] = OnServerWindowItemsPacket()
        packets[ServerConfirmTransactionPacket::class.java] = OnServerConfirmTransactionPacket()
        packets[ServerCloseWindowPacket::class.java] = OnServerCloseWindowPacket()

        //Position
        packets[ServerEntityPositionPacket::class.java] = OnServerEntityPositionPacket()
        packets[ServerEntityTeleportPacket::class.java] = OnServerEntityTeleportPacket()
        packets[ServerPlayerPositionRotationPacket::class.java] = OnServerPlayerPositionRotationPacket()
        packets[ServerSpawnPositionPacket::class.java] = OnServerSpawnPositionPacket()
        packets[ServerEntityRotationPacket::class.java] = OnServerEntityRotationPacket()
        packets[ServerEntityPositionRotationPacket::class.java] = OnServerEntityPositionRotationPacket()
    }

    init {
        registerPackets()
    }
}
