package com.exorath.game.api;

import com.exorath.game.GameAPI;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by TOON on 8/23/2015.
 */
public abstract class GameRunnable extends BukkitRunnable {
    private UUID currentUuid;
    public GameRunnable(){
        currentUuid = GameAPI.getInstance().getGame().getUUID();//TODO: Set this to currentGame
    }
    private boolean isRunnable() {
        //if(currentUuid != GameAPI.getInstance().getGame().getUUID())
        //  return false
        return true;
    }
    @Override
    public void run(){
        if(!isRunnable()) return;
        runThisGame();
    }
    public abstract void runThisGame();
}
