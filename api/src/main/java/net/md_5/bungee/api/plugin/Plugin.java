package net.md_5.bungee.api.plugin;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Injector;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ConfigurationAdapter;
import net.md_5.bungee.api.scheduler.GroupedThreadFactory;
import tc.oc.minecraft.api.event.EventRegistry;
import tc.oc.minecraft.api.event.ListenerContext;
import tc.oc.exception.ExceptionHandler;

/**
 * Represents any Plugin that may be loaded at runtime to enhance existing
 * functionality.
 */
public class Plugin implements tc.oc.minecraft.api.plugin.Plugin
{

    @Getter
    private PluginDescription description;
    @Getter
    private ProxyServer proxy;
    @Getter
    private File file;
    @Getter
    private Logger logger;

    private boolean enabled;

    @Inject private Injector injector;
    @Inject private ExceptionHandler exceptionHandler;
    @Inject private EventRegistry eventRegistry;
    @Inject private Provider<ListenerContext> listenerContext;

    protected void assertInjected() {
        if(injector == null) {
            throw new IllegalStateException("Not available until plugin has been injected");
        }
    }

    @Override
    public Injector injector() {
        assertInjected();
        return injector;
    }

    @Override
    public ExceptionHandler exceptionHandler() {
        assertInjected();
        return exceptionHandler;
    }

    @Override
    public EventRegistry eventRegistry() {
        assertInjected();
        return eventRegistry;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public final void preEnable() {
        enabled = true;
        listenerContext.get().enable();
    }

    public final void postDisable() {
        try {
            listenerContext.get().disable();
        } finally {
            enabled = false;
        }
    }

    /**
     * Called when the plugin has just been loaded. Most of the proxy will not
     * be initialized, so only use it for registering
     * {@link ConfigurationAdapter}'s and other predefined behavior.
     */
    public void onLoad()
    {
    }

    /**
     * Called when this plugin is enabled.
     */
    public void onEnable()
    {
    }

    /**
     * Called when this plugin is disabled.
     */
    public void onDisable()
    {
    }

    @Override
    public ProxyServer getServer()
    {
        return getProxy();
    }

    /**
     * Gets the data folder where this plugin may store arbitrary data. It will
     * be a child of {@link ProxyServer#getPluginsFolder()}.
     *
     * @return the data folder of this plugin
     */
    public final File getDataFolder()
    {
        return new File( getProxy().getPluginsFolder(), getDescription().getName() );
    }

    @Override
    public InputStream getResource(String name)
    {
        return getResourceAsStream( name );
    }

    /**
     * Get a resource from within this plugins jar or container. Care must be
     * taken to close the returned stream.
     *
     * @param name the full path name of this resource
     * @return the stream for getting this resource, or null if it does not
     * exist
     */
    public final InputStream getResourceAsStream(String name)
    {
        return getClass().getClassLoader().getResourceAsStream( name );
    }

    /**
     * Called by the loader to initialize the fields in this plugin.
     *
     * @param proxy current proxy instance
     * @param description the description that describes this plugin
     */
    final void init(ProxyServer proxy, PluginDescription description)
    {
        this.proxy = proxy;
        this.description = description;
        this.file = description.getFile();
        this.logger = PluginLogger.get( this );
    }

    //
    private ExecutorService service;

    @Deprecated
    public ExecutorService getExecutorService()
    {
        if ( service == null )
        {
            String name = ( getDescription() == null ) ? "unknown" : getDescription().getName();
            service = Executors.newCachedThreadPool( new ThreadFactoryBuilder().setNameFormat( name + " Pool Thread #%1$d" )
                    .setThreadFactory( new GroupedThreadFactory( this, name ) ).build() );
        }
        return service;
    }
    //
}
