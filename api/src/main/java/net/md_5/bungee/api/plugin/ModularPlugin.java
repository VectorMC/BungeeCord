package net.md_5.bungee.api.plugin;

import com.google.inject.Module;
import net.avicus.inject.ProtectedBinder;

class ModularPlugin extends Plugin {

    private final Module module;

    ModularPlugin(Module module) {
        this.module = module;
    }

    @Override
    public void configure(ProtectedBinder binder) {
        binder.install(module);
    }
}
