package com.exorath.game.api.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by too on 23/05/2015.
 * This is the main Lobby class, it handles most of the lobby stuff (Area where players get tp'ed to between games).
 */
public class Lobby {
    private static final String DEFAULT_WORLD_NAME = "Lobby";
    private LobbyProperties properties;
    public Lobby(){
        properties = new LobbyProperties();
        setupWorld();
    }
    private void setupWorld(){
        setWorld(Bukkit.getWorld(DEFAULT_WORLD_NAME));
    }
    public LobbyProperties getProperties(){
        return properties;
    }
    protected void setWorld(World world){
        properties.set(LobbyProperty.WORLD, world);
    }
    public World getWorld(){
        World world = (World) properties.get(LobbyProperty.WORLD);
        if(world == null) setupWorld();
        return world;
    }
    public Lobby enable(){
        properties.set(LobbyProperty.ENABLED, true);
        return this;
    }
    public boolean isEnabled(){
        return (boolean) properties.get(LobbyProperty.ENABLED, false);
    }
    public void setSpawnLocation(int x, int y, int z){
        properties.set(LobbyProperty.LOBBY_SPAWN, new Location(getWorld(),x, y, z));
    }
    public Location getSpawnLocation(){
        return (Location) properties.get(LobbyProperty.LOBBY_SPAWN, new Location(getWorld(), 0, 0, 0));
    }
}
