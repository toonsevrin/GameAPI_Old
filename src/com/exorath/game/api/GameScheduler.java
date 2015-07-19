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
     * Runs a task later.
     * 
     * @param runnable
     *            The runnable to be run.
     * @param delay
     *            The delay in ticks.
     * @return The BukkitTask.
     */
    public BukkitTask runTaskLater( Runnable runnable, long delay ) {
        return Bukkit.getScheduler().runTaskLater( GameAPI.getInstance(), runnable, delay );
    }
    
    /**
     * Runs a task timer.
     * 
     * @param runnable
     *            The runnable to be run.
     * @param delay
     *            The delay in ticks before starting.
     * @param period
     *            The delay in ticks between executions.
     * @return The BukkitTask.
     */
    public BukkitTask runTaskTimer( Runnable runnable, long delay, long period ) {
        return Bukkit.getScheduler().runTaskTimer( GameAPI.getInstance(), runnable, delay, period );
    }
    
}
