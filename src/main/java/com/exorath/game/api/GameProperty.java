package com.exorath.game.api;

import org.bukkit.GameMode;

/**
 * @author toon
 */
public class GameProperty extends BasePlayerProperty {

    public static final Property NAME = Property.get("game.name", "The name of the game.", Game.DEFAULT_GAME_NAME);
    public static final Property DESCRIPTION = Property.get("game.description", "The description of the game.",
            Game.DEFAULT_GAME_DESCRIPTION);
    public static final Property ALLOW_SPECTATING = Property.get("game.spectating", "Whether or not spectating is enabled.",
            false);
    public static final Property DEFAULT_GAMEMODE = Property.get("game.gamemode", "The default gamemode for the game",
            GameMode.ADVENTURE);
    public static final Property WORLD_NAME = Property.get("game.world", "The name of the main world", "world");

}
