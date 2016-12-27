package net.md_5.bungee.api.plugin;

import javax.inject.Singleton;

import com.google.inject.Provides;
import net.md_5.bungee.config.Configuration;
import tc.oc.exception.ExceptionHandler;
import tc.oc.inject.ProtectedModule;
import tc.oc.minecraft.api.event.EventRegistry;
import tc.oc.minecraft.api.event.ListenerBinder;
import tc.oc.minecraft.api.plugin.PluginExceptionHandler;
import tc.oc.minecraft.api.scheduler.Scheduler;

public class PluginModule extends ProtectedModule {

    @Override
    protected void configure() {
        new ListenerBinder(binder());

        bind(tc.oc.minecraft.api.plugin.Plugin.class).to(Plugin.class);
        bind(tc.oc.minecraft.api.plugin.PluginDescription.class).to(PluginDescription.class);
        bind(tc.oc.minecraft.api.configuration.Configuration.class).to(Configuration.class);

        bind(ExceptionHandler.class).to(PluginExceptionHandler.class).in(Singleton.class);
        bind(EventRegistry.class).to(PluginEventRegistry.class).in(Singleton.class);
        bind(Scheduler.class).to(PluginScheduler.class).in(Singleton.class);
    }

    @Provides
    PluginDescription description(Plugin plugin) {
        return plugin.getDescription();
    }

    @Provides
    PluginLogger logger(Plugin plugin) {
        return (PluginLogger) plugin.getLogger();
    }
}
