package com.exorath.game;

import com.exorath.game.api.database.SQLManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class
 */
public class GameAPI extends JavaPlugin {
    private static GameAPI instance;
    public static final String PREFIX = "GAPI_";

    private static SQLManager sqlManager;
    
    @Override
    public void onEnable() {
        instance = this;
        //TODO: Load these from config
        this.sqlManager = new SQLManager("localhost",1234,"database", "username","password");
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
    public static void error( String error) {
        System.out.println( "GameAPI ERROR: " + error );
    }
    public static void printConsole( String message) {
        System.out.println( "GameAPI console: " + message);
    }
    public static GameAPI getInstance(){
        return instance;
    }
    public static SQLManager getSQLManager(){return sqlManager;}
}
