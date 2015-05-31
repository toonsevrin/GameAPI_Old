package com.exorath.game.api;

/**
 * Created by too on 27/05/2015.
 */
public class BasePlayerProperty {
    
    public static final Property
    PVP = Property.get( "pvp", "Whether or not pvp is allowed", false ),
    BLOCK_BREAK = Property.get( "blockbreak", "Wether or not block breaking is allowed", false ),
    BLOCK_PLACE = Property.get( "blockplace", "Whether or not block placement is allowed", false ),
    INTERACT = Property.get( "interact", "Whether or not interaction is allowed", false ),
    CHAT = Property.get( "chat", "Whether or not chatting is allowed", true );
    
}
