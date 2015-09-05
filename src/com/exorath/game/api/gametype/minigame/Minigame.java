package com.exorath.game.api.gametype.minigame;

import com.exorath.game.api.Game;
import com.exorath.game.api.Property;

/**
 * @author Nick Robson
 */
public class Minigame extends Game {

    public static final Property MIN_PLAYERS = Property.get( "minplayers", "Minimal amount of players in team", 2 );
    public static final Property START_DELAY = Property.get( "startdelay", "Waiting time after there are enough players before game starts", 200);

    private MinigameStateManager stateManager = new MinigameStateManager(this);

    public Minigame(){

    }
    public boolean hasMinPlayers(){
        return getPlayerCount() >= getProperties().as(Minigame.MIN_PLAYERS, Integer.class);
    }
    public MinigameStateManager getStateManager() {
        return stateManager;
    }
}
