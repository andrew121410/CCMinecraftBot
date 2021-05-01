package com.andrew121410.mc.ccminecraftbot.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

object ConfigUtils {
    @Throws
    fun loadConfig(): CCMinecraftBotJacksonConfig? {
        val objectMapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
        val configFolder = File("config")
        val configFile = File(configFolder, "config.yml")
        if (!configFolder.exists()) {
            configFolder.mkdir()
            objectMapper.writeValue(configFile, CCMinecraftBotJacksonConfig())
            return null
        }
        return objectMapper.readValue(configFile, CCMinecraftBotJacksonConfig::class.java)
    }
}