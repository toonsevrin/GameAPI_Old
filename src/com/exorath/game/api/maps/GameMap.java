package com.exorath.game.api.maps;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import com.exorath.game.api.Game;

/**
 * @author Nick Robson
 */
public class GameMap {
    
    public static GameMap get( ConfigurationSection cfg ) {
        String name = cfg.getString( "name", null );
        String author = cfg.getString( "author", "Unknown" );
        String version = cfg.getString( "version", null );
        String gameName = cfg.getString( "gameName", null );
        
        if ( name == null || gameName == null ) {
            return null;
        } else {
            return new GameMap( name, author, version, gameName );
        }
    }
    
    private String name, author, version, gameName;
    
    public GameMap( String name, String author, String version, String gameName ) {
        this.name = name;
        this.author = author;
        this.version = version;
        this.gameName = gameName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getGameName() {
        return this.gameName;
    }
    
    public boolean isValid( Game game ) {
        return game.getName().equals( this.gameName );
    }
    
    public String[] getDisplayString() {
        String pre = ChatColor.YELLOW + "=====================";
        String n = ChatColor.AQUA + "Map: " + ChatColor.RED + this.name + ( this.version == null ? "" : " v" + this.version );
        String a = ChatColor.AQUA + "Author: " + ChatColor.RED + this.author;
        String suf = ChatColor.YELLOW + "=====================";
        return new String[] { pre, n, a, suf };
    }
    
}
