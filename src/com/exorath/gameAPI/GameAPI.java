package com.exorath.gameAPI;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main class
 */
public class GameAPI extends JavaPlugin{
    public static final String PREFIX = "GAPI_";

    @Override
    public void onEnable(){

    }
    @Override
    public void onDisable(){

    }

    /**
     * Prints an error to the console
     * @param error message you want to print.
     */
    public static void error(String error){
       System.out.println("GameAPI ERROR: " + error);
    }
}
