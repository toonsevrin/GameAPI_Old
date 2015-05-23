package com.exorath.gameAPI.game.lobby;

import com.exorath.gameAPI.GameAPI;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default lobby property keys.
 */
public enum LobbyProperty {
    //primary
    LOBBY_SPAWN("lobbyspawn", "Default spawn in lobby", Location.class),
    WORLD("world", "Lobby world. World named 'Lobby' by default", World.class),
    ENABLED("enabled", "Wether or not the lobby is enabled", Boolean.class),
    //Interaction
    PVP("pvp", "Whether or not pvp is allowed in the lobby", Boolean.class),
    BLOCK_BREAK("blockbreak", "Wether or not block breaking is allowed in the lobby", Boolean.class),
    BLOCK_PLACE("blockplace", "Whether or not block placement is allowed in the lobby", Boolean.class),
    INTERACT("interact", "Whether or interaction is allowed in the lobby", Boolean.class);
    private String key;
    private String description;
    private Class type;
    LobbyProperty(String key, String description, Class type){
        this.key = GameAPI.PREFIX + key;
        this.description = description;
        this.type = type;
    }
    public String getKey(){
        return key;
    }
    public String getDescription(){
        return description;
    }
    public Class getType(){
        return type;
    }
}
