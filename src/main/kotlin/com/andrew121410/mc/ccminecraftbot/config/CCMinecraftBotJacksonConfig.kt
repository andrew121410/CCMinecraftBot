package com.andrew121410.mc.ccminecraftbot.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CCMinecraftBotJacksonConfig {

    @JsonProperty("minecraft-username")
    private String minecraftUsername = "UsernameHere";

    @JsonProperty("minecraft-password")
    private String minecraftPassword = "PasswordHere";

    @JsonProperty("server-host")
    private String serverHost = "ServerIpHere";

    @JsonProperty("server-port")
    private Integer serverPort = 25565;
}
