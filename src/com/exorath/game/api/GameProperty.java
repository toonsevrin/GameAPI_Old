package com.exorath.game.api;


/**
 * Created by too on 23/05/2015.
 * This is an class with all default game property keys.
 */
public class GameProperty {
    
    public static final Property
            
            NAME = Property.get( "name", "The name of the game.", Game.DEFAULT_GAME_NAME ),
            PVP = Property.get( "pvp", "Whether or not pvp is allowed", false ),
            BLOCK_BREAK = Property.get( "blockbreak", "Wether or not block breaking is allowed", false ),
            BLOCK_PLACE = Property.get( "blockplace", "Whether or not block placement is allowed", false ),
            INTERACT = Property.get( "interact", "Whether or not interaction is allowed.", false );
    
}
