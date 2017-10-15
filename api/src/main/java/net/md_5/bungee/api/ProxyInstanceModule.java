package net.md_5.bungee.api;

import java.util.Collection;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginInstanceModule;
import net.avicus.inject.KeyedModule;
import net.avicus.inject.ProtectedBinder;

public class ProxyInstanceModule extends KeyedModule {

    private final ProxyServer proxy;
    private final Collection<Plugin> plugins;

    public ProxyInstanceModule(ProxyServer proxy, Collection<Plugin> plugins) {
        super(proxy);
        this.proxy = proxy;
        this.plugins = plugins;
    }

    @Override
    protected void configure() {
        install(new ProxyModule());
        bind(ProxyServer.class).toInstance(proxy);

        for(Plugin plugin : plugins) {
            ProtectedBinder.newProtectedBinder(binder())
                           .install(new PluginInstanceModule(plugin));
        }
    }
}
