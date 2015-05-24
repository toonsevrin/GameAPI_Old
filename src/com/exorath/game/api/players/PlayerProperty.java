package com.exorath.game.api.players;

import com.exorath.game.GameAPI;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default player property keys.
 */
public enum PlayerProperty {
    //primary

    //Interaction
    PVP("pvp", "Whether or not pvp is allowed for this player", Boolean.class),
    BLOCK_BREAK("blockbreak", "Wether or not block breaking is allowed in for this player", Boolean.class),
    BLOCK_PLACE("blockplace", "Whether or not block placement is allowed for this player", Boolean.class),
    INTERACT("interact", "Whether or interaction is allowed for this player", Boolean.class);
    private String key;
    private String description;
    private Class type;
    PlayerProperty(String key, String description, Class type){
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
