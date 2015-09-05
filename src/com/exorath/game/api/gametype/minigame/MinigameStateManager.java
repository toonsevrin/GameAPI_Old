package com.exorath.game.api.gametype.minigame;

import com.exorath.game.api.GameState;
import com.exorath.game.api.gametype.minigame.countdown.MinigameCountdown;

/**
 * Created by Toon on 9/1/2015.
 * Not functional yet
 */
public class MinigameStateManager {
    private Minigame minigame;
    private MinigameCountdown countdown = new MinigameCountdown(minigame);

    public MinigameStateManager(Minigame minigame){
        this.minigame = minigame;
    }
    public void checkStart(){
        if(minigame.getState() != GameState.WAITING)
            return;
        if(minigame.getPlayerCount() >= minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class)){
            countdown.start();
        }
    }
    public void checkStop(){
        if(minigame.getState() != GameState.WAITING)
            return;
        if(minigame.getPlayerCount() <= minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class)){
            countdown.stop();
        }
    }

    //** State Loop [WAITING -> STARTING -> INGAME -> FINISHING -> RESETTING] **//
    public void start(){
        if(minigame.getState() != GameState.WAITING)
            throw new IllegalStateException("Tried to change state from " + minigame.getState() + " to " + GameState.STARTING);
        minigame.setState(GameState.STARTING);
        //run onStart
        setIngame();
    }
    protected void setIngame(){
        if(minigame.getState() != GameState.STARTING)
            throw new IllegalStateException("Tried to change state from " + minigame.getState() + " to " + GameState.INGAME);
        minigame.setState(GameState.INGAME);
        //run onIngame
    }
    public void stop(){
        if(minigame.getState() != GameState.INGAME)
            throw new IllegalStateException("Tried to change state from " + minigame.getState() + " to " + GameState.FINISHING);
        minigame.setState(GameState.FINISHING);
        //run onFinishing
        setResetting();

    }
    public void setResetting(){
        if(minigame.getState() != GameState.FINISHING)
            throw new IllegalStateException("Tried to change state from " + minigame.getState() + " to " + GameState.RESETTING);
            minigame.setState(GameState.RESETTING);
        //run onResetting
        setWaiting();
    }
    public void setWaiting(){
        if(minigame.getState() != GameState.RESETTING)
            throw new IllegalStateException("Tried to change state from " + minigame.getState() + " to " + GameState.WAITING);
        minigame.setState(GameState.WAITING);
    }
}
