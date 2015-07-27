package com.exorath.game;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.exorath.game.api.Game;
import com.exorath.game.api.config.ConfigurationManager;
import com.exorath.game.api.database.SQLManager;
import com.exorath.game.api.nms.NMS;
import com.exorath.game.api.nms.NMSProvider;
import com.exorath.game.api.player.GamePlayer;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.yoshigenius.lib.util.GameUtil;

/**
 * The main class.
 */
public class GameAPI extends JavaPlugin {
    
    public static final Version CURRENT_VERSION = Version.from( "GameAPI", "0.0.1", 1, 0 ); // API Version 0 means in Development. Change for Alpha/Beta.
    
    private static SQLManager sqlManager;
    
    private FileConfiguration versionsConfig;
    
    @Override
    public void onEnable() {
        
        File databaseConfigFile = GameAPI.getConfigurationManager().getConfigFile( this, "database" );
        
        GameAPI.getConfigurationManager().saveResource( this, "configs/database", databaseConfigFile, false );
        
        FileConfiguration databaseConfig = GameAPI.getConfigurationManager().getConfig( databaseConfigFile );
        
        GameAPI.sqlManager = new SQLManager( databaseConfig.getString( "host" ), databaseConfig.getInt( "port" ),
                databaseConfig.getString( "database" ), databaseConfig.getString( "username" ),
                databaseConfig.getString( "password" ) );
        
        String serverPackage = this.getServer().getClass().getPackage().getName();
        String versionPackage = serverPackage.substring( serverPackage.lastIndexOf( '.' ) );
        try {
            Class<? extends NMSProvider> c = Class.forName( NMSProvider.class.getPackage() + "." + versionPackage + ".NMSProviderImpl" )
                    .asSubclass( NMSProvider.class );
            NMSProvider provider = c.newInstance();
            NMS.set( provider );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        
        this.versionsConfig = YamlConfiguration.loadConfiguration( new File( this.getDataFolder(), "versions.yml" ) );
    }
    
    @Override
    public void onDisable() {
        
    }
    
    @Override
    public File getFile() {
        return super.getFile();
    }
    
    /**
     * Prints an error to the console
     * 
     * @param error
     *            message you want to print.
     */
    public static void error( String error ) {
        GameAPI.getInstance().getLogger().severe( error );
    }
    
    public static void printConsole( String message ) {
        GameAPI.getInstance().getLogger().info( message );
    }
    
    public static GameAPI getInstance() {
        return JavaPlugin.getPlugin( GameAPI.class );
    }
    
    public static SQLManager getSQLManager() {
        return GameAPI.sqlManager;
    }
    
    public static ConfigurationManager getConfigurationManager() {
        return ConfigurationManager.INSTANCE;
    }
    
    public FileConfiguration getVersionsConfig() {
        return this.versionsConfig;
    }
    
    public void saveVersionsConfig() {
        
    }
    
    public static void sendPlayerToServer( Player player, String server ) {
        if ( player != null && server != null ) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF( "Connect" );
            out.writeUTF( server );
            GameUtil.sendPluginMessage( player, "BungeeCord", out.toByteArray() );
        }
    }
    
    public static void sendPlayerToServer( GamePlayer player, String server ) {
        if ( player != null && player.isOnline() ) {
            GameAPI.sendPlayerToServer( player.getBukkitPlayer(), server );
        }
    }
    
    public File getDataFolder( Game game ) {
        return new File( this.getDataFolder(), game.getName().toLowerCase().replaceAll( " ", "_" ) );
    }
    
}
