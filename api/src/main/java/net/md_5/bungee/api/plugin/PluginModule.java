package net.md_5.bungee.api.plugin;

import javax.inject.Singleton;

import com.google.inject.Provides;
import net.md_5.bungee.config.Configuration;
import net.avicus.exception.ExceptionHandler;
import net.avicus.inject.ProtectedModule;
import net.avicus.minecraft.api.event.EventRegistry;
import net.avicus.minecraft.api.event.ListenerBinder;
import net.avicus.minecraft.api.plugin.PluginExceptionHandler;
import net.avicus.minecraft.api.scheduler.Scheduler;

public class PluginModule extends ProtectedModule {

    @Override
    protected void configure() {
        new ListenerBinder(binder());

        bind(net.avicus.minecraft.api.plugin.Plugin.class).to(Plugin.class);
        bind(net.avicus.minecraft.api.plugin.PluginDescription.class).to(PluginDescription.class);
        bind(net.avicus.minecraft.api.configuration.Configuration.class).to(Configuration.class);

        bind(ExceptionHandler.class).to(PluginExceptionHandler.class).in(Singleton.class);
        bind(EventRegistry.class).to(PluginEventRegistry.class).in(Singleton.class);
        bind(Scheduler.class).to(PluginScheduler.class).in(Singleton.class);

        bind(PluginConfigurationLoader.class).in(Singleton.class);
    }

    @Provides
    PluginDescription description(Plugin plugin) {
        return plugin.getDescription();
    }

    @Provides
    PluginLogger logger(Plugin plugin) {
        return (PluginLogger) plugin.getLogger();
    }

    @Provides @Singleton
    Configuration configuration(PluginConfigurationLoader loader) {
        return loader.loadConfig();
    }
}
