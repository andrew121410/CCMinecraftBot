package com.andrew121410.mc.ccminecraftbot.utils

import com.andrew121410.mc.ccminecraftbot.CCBotMinecraft
import com.andrew121410.mc.ccminecraftbot.objects.Block
import com.andrew121410.mc.ccminecraftbot.objects.BoundingBox
import com.andrew121410.mc.ccminecraftbot.objects.Item
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.jvm.Throws

object ResourceManager {

    val AIR_BLOCK = Block(
        id = 0,
        displayName = "Air",
        name = "air",
        hardness = 0.0f,
        stackSize = 0,
        diggable = true,
        boundingBox = BoundingBox.empty,
        material = null,
        drops = emptyArray(),
        emitLight = 0,
        filterLight = 0,
        transparent = true,
        resistance = 0
    )

    private val mapper = ObjectMapper().registerModule(KotlinModule())

    var dataPaths = HashMap<String, HashMap<String, HashMap<String, String>>>()

    var blocks = HashMap<Int, Block>()
    var items = HashMap<Int, Item>()

    fun load() {
        loadPaths()
        loadBlocks()
        loadItems()
    }

    fun loadPaths() {
        val jsonString = getResourcesInJarAsText("/minecraft-data/data/dataPaths.json")
        dataPaths = mapper.readValue(jsonString)
    }

    fun loadBlocks() {
        val blocksPath = dataPaths["pc"]?.get("1.16.5")?.get("blocks") ?: return
        val blocksJson = getResourcesInJarAsText("/minecraft-data/data/$blocksPath/blocks.json")
        val blocksArray = mapper.readValue<Array<Block>>(blocksJson)
        for (block in blocksArray) blocks[block.id] = block
        if (!blocks.containsKey(0)) blocks[0] = AIR_BLOCK
    }

    fun loadItems() {
        val itemsPath = dataPaths["pc"]?.get("1.16.5")?.get("items") ?: throw NullPointerException("itemsPath is null")
        val itemsJson = getResourcesInJarAsText("/minecraft-data/data/$itemsPath/items.json")
        val itemsArray = mapper.readValue<Array<Item>>(itemsJson)
        for (item in itemsArray) items[item.id] = item
    }

    @Throws
    fun readFromInputStream(inputStream: InputStream): String {
        val resultStringBuilder = StringBuilder()
        BufferedReader(InputStreamReader(inputStream)).use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                resultStringBuilder.append(line).append("\n")
            }
        }
        return resultStringBuilder.toString()
    }

    @Throws
    fun getResourcesInJarAsText(path: String): String {
        val inputStream =
            CCBotMinecraft::class.java.getResourceAsStream(path)
                ?: throw NullPointerException("inputStream was null")
        return readFromInputStream(inputStream)
    }
}