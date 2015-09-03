package com.exorath.game.api.gametype.minigame;

import com.exorath.game.api.GameState;

/**
 * Created by Toon on 9/1/2015.
 * Not functional yet
 */
public class MinigameStateManager {
    private Minigame minigame;
    private GameState state = GameState.WAITING;
    public MinigameStateManager(Minigame minigame){
        this.minigame = minigame;
    }
    public void checkStart(){
        if(state != GameState.WAITING)
            return;
        if(minigame.getPlayerCount() >= minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class)){
            //Start countdown
        }
    }
    public void checkStop(){
        if(state != GameState.WAITING)
            return;
        if(minigame.getPlayerCount() >= minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class)){
            //Stop countdown
        }
    }

    //** State Loop [WAITING -> STARTING -> INGAME -> FINISHING -> RESETTING] **//
    protected void start(){
        if(state != GameState.WAITING)
            throw new IllegalStateException("Tried to change state from " + state + " to " + GameState.STARTING);
        state = GameState.STARTING;
        //run onStart
        setIngame();
    }
    protected void setIngame(){
        if(state != GameState.STARTING)
            throw new IllegalStateException("Tried to change state from " + state + " to " + GameState.INGAME);
        state = GameState.INGAME;
        //run onIngame
    }
    public void stop(){
        if(state != GameState.INGAME)
            throw new IllegalStateException("Tried to change state from " + state + " to " + GameState.FINISHING);
        state = GameState.FINISHING;
        //run onFinishing
        setResetting();

    }
    public void setResetting(){
        if(state != GameState.FINISHING)
            throw new IllegalStateException("Tried to change state from " + state + " to " + GameState.RESETTING);
        state = GameState.RESETTING;
        //run onResetting
        setWaiting();
    }
    public void setWaiting(){
        if(state != GameState.RESETTING)
            throw new IllegalStateException("Tried to change state from " + state + " to " + GameState.WAITING);
        state = GameState.WAITING;
    }
}
