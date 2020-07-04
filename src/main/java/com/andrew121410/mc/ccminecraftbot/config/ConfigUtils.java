package com.andrew121410.mc.ccminecraftbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;

import java.io.File;

public class ConfigUtils {

    @SneakyThrows
    public static CCMinecraftBotJacksonConfig loadMainConfig() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        File configFolder = new File("config");
        File configFile = new File(configFolder, "config.yml");
        if (!configFolder.exists()) {
            configFolder.mkdir();
            objectMapper.writeValue(configFile, new CCMinecraftBotJacksonConfig());
            return null;
        }
        return objectMapper.readValue(configFile, CCMinecraftBotJacksonConfig.class);
    }
}
