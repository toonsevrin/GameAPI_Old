package com.exorath.game;

import org.bukkit.plugin.java.JavaPlugin;

import com.exorath.game.api.database.SQLManager;

/**
 * The main class
 */
public class GameAPI extends JavaPlugin {
    
    public static final Version CURRENT_VERSION = Version.from( "GameAPI", "0.0.1", 1, 0 ); // API Version 0 means in Development. Change for Alpha/Beta.
    
    private static SQLManager sqlManager;
    
    @Override
    public void onEnable() {
        //TODO: Load these from config
        GameAPI.sqlManager = new SQLManager( "localhost", 1234, "database", "username", "password" );
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
}
