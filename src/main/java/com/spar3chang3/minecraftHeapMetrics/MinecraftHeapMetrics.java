package com.spar3chang3.minecraftHeapMetrics;

import org.bukkit.plugin.java.JavaPlugin;
import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import javax.inject.Inject;

@Plugin(
        id = "minecraftheapmetrics",
        name = "Minecraft Heap Metrics",
        version = "1.0-SNAPSHOT",
        url = "#",
        description = "A plugin to collect server heap metrics and hand them to another Java process",
        authors = "Me! Dawyk Baker :3"
)

public final class MinecraftHeapMetrics extends JavaPlugin {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

   private long memUsed;
   private long memMax;
   private long memAlloc;

   MemoryMXBean memBean;
   MemoryUsage heapMem;

    @Inject
    public MinecraftHeapMetrics(ProxyServer server, Logger logger @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;

        logger.info("Heap Metrics loaded! Sending startup data and creating local process server");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.startHeapMetrics();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void updateHeapMetrics() {
        memBean = ManagementFactory.getMemoryMXBean();
        heapMem = memBean.getHeapMemoryUsage();

        this.memUsed = heapMem.getUsed();
        this.memAlloc = heapMem.getCommitted();
    }

    private void startHeapMetrics() {
        this.updateHeapMetrics();
        this.memMax = heapMem.getMax();
    }
}
