package com.exorath.game.api;

/**
 * @author toon
 */
public class GameProperty {
    
    public static final Property NAME = Property.get( "name", "The name of the game.", Game.DEFAULT_GAME_NAME );
    public static final Property DESCRIPTION = Property.get( "description", "The description of the game.", Game.DEFAULT_GAME_DESCRIPTION );
    
}
