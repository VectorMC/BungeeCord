package net.md_5.bungee;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.command.ConsoleCommandSender;


public class BungeeCordLauncher
{
    public static void main(String[] args) throws Exception
    {
        Security.setProperty( "networkaddress.cache.ttl", "30" );
        Security.setProperty( "networkaddress.cache.negative.ttl", "10" );
        
        OptionParser parser = new OptionParser();
        parser.allowsUnrecognizedOptions();
        parser.acceptsAll( Arrays.asList( "v", "version") );
        parser.acceptsAll( Arrays.asList( "noconsole" ) );

        OptionSet options = parser.parse( args );

        if ( options.has("version") )
        {
            System.out.println(Bootstrap.class.getPackage().getImplementationVersion());
            return;
        }

        if ( BungeeCord.class.getPackage().getSpecificationVersion() != null && System.getProperty( "IReallyKnowWhatIAmDoingISwear" ) == null)
        {
            String version = BungeeCord.class.getPackage().getSpecificationVersion();
            
            if ( version.equalsIgnoreCase("unknown") )
            {
                System.err.println( "*** You are using a self compiled version ***" );
                System.err.println( "*** Please make sure your server is up to date ***" );
                System.err.println( "*** Using current version without warranty ***" );
            } else
            {
                System.err.println( "*** Warning, this build is outdated ***" );
                System.err.println( "*** Please download a new build from http://ci.md-5.net/job/BungeeCord ***" );
                System.err.println( "*** You will get NO support regarding this build ***" );
            }
        }

        BungeeCord bungee = new BungeeCord();
        ProxyServer.setInstance( bungee );
        bungee.getLogger().info( "Enabled BungeeCord version " + bungee.getVersion() );
        bungee.start();

        if ( !options.has( "noconsole" ) )
        {
            String line;
            while ( bungee.isRunning && ( line = bungee.getConsoleReader().readLine( ">" ) ) != null )
            {
                if ( !bungee.getPluginManager().dispatchCommand(ConsoleCommandSender.getInstance(), line ) )
                {
                    bungee.getConsole().sendMessage( ChatColor.RED + "Command not found" );
                }
            }
        }
    }
}
