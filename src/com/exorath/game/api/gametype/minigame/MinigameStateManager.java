package com.exorath.game.api.gametype.minigame;

import com.exorath.game.GameAPI;
import com.exorath.game.api.GameRunnable;
import com.exorath.game.api.GameState;
import com.exorath.game.api.StopCause;
import com.exorath.game.api.gametype.minigame.countdown.MinigameCountdown;
import com.exorath.game.api.message.GameMessenger;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamManager;

/**
 * Created by Toon on 9/1/2015.
 * Not functional yet
 */
public class MinigameStateManager {

    private Minigame minigame;
    private MinigameCountdown countdown;

    public MinigameStateManager(Minigame minigame) {
        this.minigame = minigame;
        minigame.setState(GameState.WAITING);
        countdown = new MinigameCountdown(minigame);
    }

    //Run this when a player joins
    public void checkStart() {
        if (minigame.getState() != GameState.WAITING)
            return;
        for(Team team : minigame.getManager(TeamManager.class).getTeams()){
            //TODO: Check if min amount of players are in this team
        }
        int min = minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class);
        int players = minigame.getPlayers().getPlayerCount();
        if (minigame.hasMinPlayers()) {
            countdown.start();
            GameMessenger.sendInfo(minigame,
                    "Game is starting in " + minigame.getProperties().as(Minigame.START_DELAY, Integer.class));
        } else
            GameMessenger.sendInfo(minigame, "Waiting for " + (min - players) + " players.");
    }

    //Run this when a player leaves
    public void checkStop() {
        if (minigame.getState() != GameState.WAITING)
            return;
        int min = minigame.getProperties().as(Minigame.MIN_PLAYERS, Integer.class);
        int players = minigame.getPlayers().getPlayerCount();
        if (players <= min) {
            countdown.stop();
            GameMessenger.sendInfo(minigame, "Countdown stopped. Waiting for " + (min - players) + " players.");
        }
    }

    //** State Loop [WAITING -> STARTING -> INGAME -> FINISHING -> RESETTING] **//
    //* Start: *//
    public void start() {
        if (minigame.getState() != GameState.WAITING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.STARTING);
        minigame.setState(GameState.STARTING);
        minigame.spawnPlayers();
        setIngame();
    }

    protected void setIngame() {
        if (minigame.getState() != GameState.STARTING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.INGAME);
        minigame.setState(GameState.INGAME);
        //Force end game if max duration is reached
        int delay = minigame.getProperties().as(Minigame.MAX_DURATION, Integer.class);
        if (delay != 0)
            new EndTask().runTaskLater(GameAPI.getInstance(), delay);

    }

    public void stop(StopCause cause) {
        if (minigame.getState() != GameState.INGAME)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.FINISHING);
        minigame.setState(GameState.FINISHING);
        minigame.reward();
        setResetting();

    }

    public void setResetting() {
        if (minigame.getState() != GameState.FINISHING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.RESETTING);
        minigame.setState(GameState.RESETTING);
        minigame.reset();
        setWaiting();
    }

    public void setWaiting() {
        if (minigame.getState() != GameState.RESETTING)
            throw new IllegalStateException(
                    "Tried to change state from " + minigame.getState() + " to " + GameState.WAITING);
        minigame.setState(GameState.WAITING);
    }

    private class EndTask extends GameRunnable {

        public EndTask() {
            super(minigame);
        }

        @Override
        public void _run() {
            if (minigame.getState() == GameState.INGAME)
                minigame.getStateManager().stop(StopCause.TIME_UP);
        }
    }
}
