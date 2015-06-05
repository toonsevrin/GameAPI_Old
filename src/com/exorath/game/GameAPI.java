package com.exorath.game;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class
 */
public class GameAPI extends JavaPlugin {
    
    public static final Version CURRENT_VERSION = Version.from( "GameAPI", "0.0.1", 1, 0 ); // API Version 0 means in Development. Change for Alpha/Beta.
    
    @Override
    public void onEnable() {
        
    }
    
    @Override
    public void onDisable() {
        
    }
    
    /**
     * Prints an error to the console
     * 
     * @param error
     *            The error message to print.
     */
    public static void error( String error ) {
        GameAPI.getInstance().getLogger().severe( error );
    }
    
    public static GameAPI getInstance() {
        return JavaPlugin.getPlugin( GameAPI.class );
    }
}
