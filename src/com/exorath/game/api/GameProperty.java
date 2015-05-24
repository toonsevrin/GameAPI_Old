package com.exorath.game.api;

import com.exorath.game.GameAPI;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default game property keys.
 */
public enum GameProperty {
    //primary
    NAME("name","The name of the game.", String.class),
    //Interaction
    PVP("pvp", "Whether or not pvp is allowed", Boolean.class),
    BLOCK_BREAK("blockbreak", "Wether or not block breaking is allowed", Boolean.class),
    BLOCK_PLACE("blockplace", "Whether or not block placement is allowed", Boolean.class),
    INTERACT("interact", "Whether or not interaction is allowed.", Boolean.class);
    private String key;
    private String description;
    private Class type;
    GameProperty(String key, String description, Class type){
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
