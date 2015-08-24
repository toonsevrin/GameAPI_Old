package com.exorath.game.api;

import org.bukkit.GameMode;

/**
 * @author toon
 */
public class GameProperty {

    public static final Property NAME = Property.get( "name", "The name of the game.", Game.DEFAULT_GAME_NAME );
    public static final Property DESCRIPTION = Property.get( "description", "The description of the game.", Game.DEFAULT_GAME_DESCRIPTION );
    public static final Property MAX_DURATION = Property.get( "maxduration", "The maximum duration of the game. 0 disables.", 0 );
    public static final Property ALLOW_SPECTATING = Property.get( "spectating", "Whether or not spectating is enabled.", false );
    public static final Property DEFAULT_GAMEMODE = Property.get( "gamemode", "The default gamemode for the game", GameMode.ADVENTURE );

}
