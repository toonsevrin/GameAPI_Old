package com.exorath.game.api.gametype.minigame;

import com.exorath.game.api.Game;
import com.exorath.game.api.Property;

/**
 * @author Nick Robson
 */
public class Minigame extends Game {

    public static final Property MIN_PLAYERS = Property.get( "minplayers", "Minimal amount of players in team", 2 );


    public Minigame(){

    }
    public boolean hasMinPlayers(){
        return getPlayerCount() >= getProperties().as(Minigame.MIN_PLAYERS, Integer.class);
    }
}
