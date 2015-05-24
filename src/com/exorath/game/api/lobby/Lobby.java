package com.exorath.game.api.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.exorath.game.api.Properties;

/**
 * Created by too on 23/05/2015.
 * This is the main Lobby class, it handles most of the lobby stuff (Area where players get tp'ed to
 * between games).
 */
public class Lobby {
    
    public static final String DEFAULT_WORLD_NAME = "Lobby";
    private Properties properties = new Properties();
    
    public Lobby() {
        this.setupWorld();
    }
    
    private void setupWorld() {
        this.setWorld( Bukkit.getWorld( Lobby.DEFAULT_WORLD_NAME ) );
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    protected void setWorld( World world ) {
        this.properties.set( LobbyProperty.WORLD, world );
    }
    
    public World getWorld() {
        World world = Bukkit.getWorld( this.properties.as( LobbyProperty.WORLD, String.class ) );
        if ( world == null ) {
            this.setupWorld();
        }
        return world;
    }
    
    public Lobby enable() {
        this.properties.set( LobbyProperty.ENABLED, true );
        return this;
    }
    
    public boolean isEnabled() {
        return (boolean) this.properties.get( LobbyProperty.ENABLED, false );
    }
    
    public void setSpawnLocation( int x, int y, int z ) {
        this.properties.set( LobbyProperty.LOBBY_SPAWN, new Location( this.getWorld(), x, y, z ) );
    }
    
    public Location getSpawnLocation() {
        return (Location) this.properties.get( LobbyProperty.LOBBY_SPAWN, new Location( this.getWorld(), 0, 0, 0 ) );
    }
}
