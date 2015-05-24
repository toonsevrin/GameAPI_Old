package com.exorath.game.api.players;

import com.exorath.game.api.Property;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default player property keys.
 */
public class PlayerProperty {
    
    public static final Property
    
    PVP = Property.get( "pvp", "Whether or not pvp is allowed for this player", false ),
    BLOCK_BREAK = Property.get( "blockbreak", "Whether or not block breaking is allowed in for this player", false ),
    BLOCK_PLACE = Property.get( "blockplace", "Whether or not block placement is allowed for this player", false ),
    INTERACT = Property.get( "interact", "Whether or interaction is allowed for this player", false );
    
}
