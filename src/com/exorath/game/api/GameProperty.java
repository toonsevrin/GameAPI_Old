package com.exorath.game.api;

/**
 * Created by too on 23/05/2015.
 * This is an class with all default game property keys.
 */
public class GameProperty {

    public static final Property
            DESCRIPTION = Property.get("description", "The description of the game.", Game.DEFAULT_GAME_DESCRIPTION),
            NAME = Property.get("name", "The name of the game.", Game.DEFAULT_GAME_NAME);

}
