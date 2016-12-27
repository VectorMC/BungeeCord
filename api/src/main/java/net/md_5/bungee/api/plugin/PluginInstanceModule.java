package net.md_5.bungee.api.plugin;

import tc.oc.inject.ProtectedModule;

public class PluginInstanceModule extends ProtectedModule {

    private final Plugin plugin;

    public PluginInstanceModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        install(new PluginModule());
        bind(Plugin.class).toInstance(plugin);
        plugin.configure(binder());
    }
}
