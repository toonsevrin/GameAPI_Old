package com.exorath.game.api;

import com.exorath.game.GameAPI;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Toon Sevrin on 8/23/2015.
 * Runnable that will not run if the game that was currently running has stopped
 */
public abstract class GameRunnable extends BukkitRunnable {
    private String currentUuid;
    public GameRunnable(){
        currentUuid = GameAPI.getInstance().getGame().getGameID().toString();
    }
    private boolean isRunnable() {
        if(!currentUuid.toString().equals( GameAPI.getInstance().getGame().getGameID().toString()))
            return false;
        return true;
    }
    @Override
    public void run(){
        if(!isRunnable())
            return;
        runThisGame();
    }
    public abstract void runThisGame();
}
