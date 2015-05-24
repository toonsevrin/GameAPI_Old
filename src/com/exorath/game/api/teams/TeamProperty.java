package com.exorath.game.api.teams;

import com.exorath.game.api.Property;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default team property keys.
 */
public class TeamProperty {
    
    public static final Property
            
            NAME = Property.get( "name", "The name of the team.", Team.DEFAULT_NAME ),
            TEAM_SIZE = Property.get( "teamsize", "How many players can join this team", Team.DEFAULT_MAX_PLAYER_AMOUNT ),
            PVP = Property.get( "pvp", "Whether or not pvp is allowed within the team", false ),
            BLOCK_BREAK = Property.get( "blockbreak", "Whether or not block breaking is allowed", false ),
            BLOCK_PLACE = Property.get( "blockplace", "Whether or not block placement is allowed", false ),
            INTERACT = Property.get( "interact", "Whether or interaction is allowed", false );
}
