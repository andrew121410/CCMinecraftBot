package com.andrew121410.mc.ccminecraftbot.config

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CCMinecraftBotJacksonConfig(
    @JsonProperty("minecraft-username") val minecraftUsername: String = "Username",
    @JsonProperty("minecraft-password") val minecraftPassword: String = "Password",
    @JsonProperty("server-host") val serverHost: String = "IpForTheServer",
    @JsonProperty("server-port") val serverPort: String = "25565"
)