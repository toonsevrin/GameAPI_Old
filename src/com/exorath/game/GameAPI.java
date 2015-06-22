package com.exorath.game;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.exorath.game.api.config.ConfigurationManager;
import com.exorath.game.api.database.SQLManager;
import com.exorath.game.api.nms.NMS;
import com.exorath.game.api.nms.v1_8_R2.MC1_8_R2_NMSProvider;
import com.exorath.game.api.nms.v1_8_R3.MC1_8_R3_NMSProvider;

/**
 * The main class
 */
public class GameAPI extends JavaPlugin {
    
    public static final Version CURRENT_VERSION = Version.from( "GameAPI", "0.0.1", 1, 0 ); // API Version 0 means in Development. Change for Alpha/Beta.
    
    private static SQLManager sqlManager;
    
    @Override
    public void onEnable() {
        
        File databaseConfigFile = GameAPI.getConfigurationManager().getConfigFile( this, "database" );
        
        GameAPI.getConfigurationManager().saveResource( this, "configs/database", databaseConfigFile, false );
        
        FileConfiguration databaseConfig = GameAPI.getConfigurationManager().getConfig( databaseConfigFile );
        
        GameAPI.sqlManager = new SQLManager( databaseConfig.getString( "host" ), databaseConfig.getInt( "port" ),
                databaseConfig.getString( "database" ), databaseConfig.getString( "username" ),
                databaseConfig.getString( "password" ) );
        
        try {
            Class.forName( "org.bukkit.craftbukkit.v1_8_R2.CraftServer" );
            NMS.set( new MC1_8_R2_NMSProvider() );
        } catch ( Exception ex ) {}
        try {
            Class.forName( "org.bukkit.craftbukkit.v1_8_R3.CraftServer" );
            NMS.set( new MC1_8_R3_NMSProvider() );
        } catch ( Exception ex ) {}
        
    }
    
    @Override
    public void onDisable() {
        
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
    
}
