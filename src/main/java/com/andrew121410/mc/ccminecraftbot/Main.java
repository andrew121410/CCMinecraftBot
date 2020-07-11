package com.andrew121410.mc.ccminecraftbot;

import com.andrew121410.mc.ccminecraftbot.commands.CommandManager;
import com.andrew121410.mc.ccminecraftbot.config.CCMinecraftBotJacksonConfig;
import com.andrew121410.mc.ccminecraftbot.config.ConfigUtils;
import com.andrew121410.mc.ccminecraftbot.packets.PacketSessionAdapter;
import com.andrew121410.mc.ccminecraftbot.player.CCPlayer;
import com.github.steveice10.mc.auth.exception.request.RequestException;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.ProxyInfo;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    @Getter
    private static Main instance;

    @Getter
    private boolean isShuttingDown = false;

    @Getter
    private ScheduledExecutorService scheduledExecutorService;

    @Getter
    private CCMinecraftBotJacksonConfig config;
    @Getter
    private Client client;
    @Getter
    private CommandManager commandManager;

    @Getter
    @Setter
    private CCPlayer player;

    private static final ProxyInfo PROXY = null;
    private static final Proxy AUTH_PROXY = Proxy.NO_PROXY;

    public static void main(String[] args) {
        new Main(args);
    }

    public Main(String[] args) {
        instance = this;
        this.config = ConfigUtils.loadConfig();
        setupMinecraftBot();
        setupScanner();
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
        commandManager = new CommandManager(this);

        client = new Client(config.getServerHost(), config.getServerPort(), protocol, new TcpSessionFactory(PROXY));
        client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, AUTH_PROXY);
        client.getSession().addListener(new PacketSessionAdapter(this));
        client.getSession().connect();
        startMainTick();
    }

    public void startMainTick() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.scheduledExecutorService.scheduleAtFixedRate(() -> {
            atomicInteger.getAndIncrement();
            if (atomicInteger.get() == 20 && CCPlayer.isReady) {
                this.getPlayer().tick();
                atomicInteger.set(0);
            }

            if (atomicInteger.get() >= 20) {
                atomicInteger.set(0);
            }
        }, 0, 50, TimeUnit.MILLISECONDS); //0.05 seconds
    }

    private void setupScanner() {
        new Thread(() -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    switch (line) {
                        case "end":
                        case "stop":
                        case "exit":
                        case "quit":
                            quit();
                        default:
                            System.out.println("Not a command?");
                    }
                }
            } catch (IOException ignored) {
            }
        }, "MyDiscordSocketBot-Scanner").start();
    }

    public void quit() {
        this.isShuttingDown = true;
        System.out.println("Shutting down.");
        this.scheduledExecutorService.shutdown();
        this.client.getSession().disconnect("OkByes");
        System.exit(1);
    }
}
