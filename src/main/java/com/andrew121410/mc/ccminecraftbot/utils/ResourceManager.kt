package com.andrew121410.mc.ccminecraftbot.utils

import com.andrew121410.mc.ccminecraftbot.objects.Block
import com.andrew121410.mc.ccminecraftbot.objects.BoundingBox
import com.andrew121410.mc.ccminecraftbot.objects.Item
import com.andrew121410.mc.ccminecraftbot.objects.Material
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.steveice10.mc.protocol.MinecraftConstants
import java.io.File

val AIR_BLOCK = Block(
    id = 0,
    displayName = "Air",
    name = "air",
    hardness = 0.0f,
    stackSize = 0,
    diggable = true,
    BoundingBox.empty,
    material = null,
    drops = emptyArray(),
    emitLight = 0,
    filterLight = 0,
    transparent = true
)

object ResourceManager {
    private val classLoader = javaClass.classLoader
    private val mapper = ObjectMapper().registerModule(KotlinModule())

    var dataPaths = HashMap<String, HashMap<String, HashMap<String, String>>>()

    var blocks = HashMap<Int, Block>()
    var items = HashMap<Int, Item>()
    var materials = HashMap<String, Material>()

    fun load(){
        loadPaths()
        loadBlocks()
        loadItems()
        loadMaterials()
    }

    fun loadPaths() {
        val pathFile = File(classLoader.getResource("minecraft-data/data/dataPaths.json")?.file ?: return)
        dataPaths = mapper.readValue(pathFile.readText())
    }

    fun loadBlocks() {
        val blocksPath = dataPaths["pc"]?.get(MinecraftConstants.GAME_VERSION)?.get("blocks") ?: return
        val blocksFile = File(classLoader.getResource("minecraft-data/data/$blocksPath/blocks.json")?.file ?: return)
        val blocksArray = mapper.readValue<Array<Block>>(blocksFile.readText())
        for (block in blocksArray) blocks[block.id] = block
        if (!blocks.containsKey(0)) blocks[0] = AIR_BLOCK
    }

    fun loadItems() {
        val itemsPath = dataPaths["pc"]?.get(MinecraftConstants.GAME_VERSION)?.get("items") ?: return
        val itemsFile = File(classLoader.getResource("minecraft-data/data/$itemsPath/items.json")?.file ?: return)
        val itemsArray = mapper.readValue<Array<Item>>(itemsFile.readText())
        for (item in itemsArray) items[item.id] = item
    }

    fun loadMaterials() {
        val materialsPath = dataPaths["pc"]?.get(MinecraftConstants.GAME_VERSION)?.get("materials") ?: return
        val materialsFile =
            File(classLoader.getResource("minecraft-data/data/$materialsPath/materials.json")?.file ?: return)
        materials = mapper.readValue(materialsFile.readText())
    }
}