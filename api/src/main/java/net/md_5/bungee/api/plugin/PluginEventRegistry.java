package net.md_5.bungee.api.plugin;

import javax.inject.Inject;

import net.avicus.minecraft.api.event.EventRegistry;
import net.avicus.minecraft.api.event.Listener;

public class PluginEventRegistry implements EventRegistry {

    private final PluginManager pluginManager;
    private final Plugin plugin;

    @Inject public PluginEventRegistry(PluginManager pluginManager, Plugin plugin) {
        this.pluginManager = pluginManager;
        this.plugin = plugin;
    }

    @Override
    public void registerListener(Listener listener) {
        pluginManager.registerListener(plugin, listener);
    }

    @Override
    public void unregisterListener(Listener listener) {
        pluginManager.unregisterListener(listener);
    }

    @Override
    public void unregisterAll() {
        pluginManager.unregisterListeners(plugin);
    }
}
