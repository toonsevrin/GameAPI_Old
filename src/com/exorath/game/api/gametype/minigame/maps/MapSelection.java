package com.exorath.game.api.gametype.minigame.maps;

/**
 * Created by TOON on 7/2/2015.
 * These are the ways a new map is selected at the end of a minigame
 * 
 * @author Toon Sevrin
 * @author Nick Robson
 */
public enum MapSelection {

    /**
     * Always run the same map.
     */
    SAME,
    /**
     * Cycle through maps (non-random).
     */
    CYCLE,
    /**
     * Cycle through maps (randomly, not the same map twice in a row, though)
     */
    RANDOM,
    /**
     * Let players vote for the map.
     */
    VOTE,

}
