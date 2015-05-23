package com.exorath.gameAPI.game.teams;

import com.exorath.gameAPI.GameAPI;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default team property keys.
 */
public enum TeamProperty {
    //primary
    NAME("name","The name of the team.", String.class),
    TEAM_SIZE("teamsize", "How many players can join this team", Integer.class),

    //Interaction
    PVP("pvp", "Whether or not pvp is allowed within the team", Boolean.class),
    BLOCK_BREAK("blockbreak", "Wether or not block breaking is allowed", Boolean.class),
    BLOCK_PLACE("blockplace", "Whether or not block placement is allowed", Boolean.class),
    INTERACT("interact", "Whether or interaction is allowed", Boolean.class);
    private String key;
    private String description;
    private Class type;
    TeamProperty(String key, String description, Class type){
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
