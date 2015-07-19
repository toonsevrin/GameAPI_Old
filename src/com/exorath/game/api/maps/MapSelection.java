package com.exorath.game.api.maps;

/**
 * Created by TOON on 7/2/2015.
 * These are the ways a new map is selected at the end of a minigame
 */
public enum MapSelection {
    DEFAULT,//Always run the default map
    CYCLE, //Cycle through all maps
    VOTE, //Let the players vote for the next map
    RANDOM; //Each time a random map is selected
}
