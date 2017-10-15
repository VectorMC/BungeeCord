package net.md_5.bungee.api;

import java.util.Collection;
import java.util.Map;

import com.google.inject.Provides;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.avicus.minecraft.api.server.Server;
import net.avicus.inject.SingletonModule;
import net.avicus.minecraft.api.command.ConsoleCommandSender;
import net.avicus.minecraft.api.plugin.PluginFinder;
import net.avicus.minecraft.api.server.LocalServer;
import net.avicus.minecraft.api.server.MinecraftServerModule;

public class ProxyModule extends SingletonModule {

    @Override
    protected void configure() {
        install(new MinecraftServerModule());

        bind(Server.class).to(LocalServer.class);
        bind(LocalServer.class).to(ProxyServer.class);
        bind(PluginFinder.class).to(PluginManager.class);
    }

    @Provides
    PluginManager pluginManager(ProxyServer proxy) {
        return proxy.getPluginManager();
    }

    @Provides
    TaskScheduler bukkitScheduler(ProxyServer proxy) {
        return proxy.getScheduler();
    }

    @Provides
    ConsoleCommandSender consoleCommandSender(ProxyServer proxy) {
        return proxy.getConsoleSender();
    }

    @Provides
    Map<String, ServerInfo> serverMap(ProxyServer proxy) {
        return proxy.getServersCopy();
    }

    @Provides
    Collection<ProxiedPlayer> players(ProxyServer proxy) {
        return proxy.getOnlinePlayers();
    }
}
