package com.andrew121410.mc.ccminecraftbot;

import com.andrew121410.mc.ccminecraftbot.commands.CommandMan;
import com.andrew121410.mc.ccminecraftbot.config.CCMinecraftBotJacksonConfig;
import com.andrew121410.mc.ccminecraftbot.config.ConfigUtils;
import com.andrew121410.mc.ccminecraftbot.packets.PacketSessionAdapter;
import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.ProxyInfo;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import lombok.Getter;
import lombok.Setter;

import java.net.Proxy;

public class Main {

    @Getter
    private static Main instance;

    @Getter
    private CCMinecraftBotJacksonConfig config;
    @Getter
    private Client client;
    @Getter
    private CommandMan commandManager;

    @Getter
    @Setter
    private CCPlayer ccPlayer;

    private static final ProxyInfo PROXY = null;
    private static final Proxy AUTH_PROXY = Proxy.NO_PROXY;

    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] args) {
        instance = this;
        this.config = ConfigUtils.loadMainConfig();
        setupMinecraftBot();
    }

    public void setupMinecraftBot() {
        MinecraftProtocol protocol;
        try {
            protocol = new MinecraftProtocol(config.getMinecraftUsername(), config.getMinecraftPassword());
            System.out.println("Successfully authenticated user.");
        } catch (RequestException e) {
            e.printStackTrace();
            return;
        }
        commandManager = new CommandMan(this);

        client = new Client(config.getServerHost(), config.getServerPort(), protocol, new TcpSessionFactory(PROXY));
        client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, AUTH_PROXY);
        client.getSession().addListener(new PacketSessionAdapter(this));
        client.getSession().connect();
    }
}
