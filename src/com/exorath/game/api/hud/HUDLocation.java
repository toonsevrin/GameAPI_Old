package com.exorath.game.api.hud;

import org.bukkit.Bukkit;

import com.exorath.game.GameAPI;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 8/9/2015.
 */
public abstract class HUDLocation implements Runnable {

    protected boolean active;
    protected GamePlayer player;
    private int currentSequence = Integer.MIN_VALUE;//This is for HUDText's with the same priority (FIFO behaviour!)

    public HUDLocation(GamePlayer player) {
        this.player = player;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GameAPI.getInstance(), this, 0, 1);
    }

    public boolean isActive() {
        return active;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public abstract void setActive(boolean active);

    /**
     * @return currentSequence + 1 and adds 1 to the currentSequence
     */
    protected int getNewSequence() {
        if (currentSequence == Integer.MAX_VALUE)
            currentSequence = Integer.MIN_VALUE;
        return currentSequence++;
    }
}
