package com.exorath.game.api;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.exorath.game.GameAPI;

/**
 * @author Nick Robson
 */
public class GameScheduler {
    
    public static GameScheduler INSTANCE = new GameScheduler();
    
    private GameScheduler() {}
    
    /**
     * @param runnable
     *            The runnable to be run.
     * @param ticks
     *            The delay in ticks.
     */
    public BukkitTask runTaskLater( Runnable runnable, long ticks ) {
        return Bukkit.getScheduler().runTaskLater( GameAPI.getInstance(), runnable, ticks );
    }
    
}
