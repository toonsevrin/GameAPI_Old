package com.exorath.game.api.hud;

import com.exorath.game.GameAPI;
import com.exorath.game.api.hud.locations.ActionBar;
import com.exorath.game.api.hud.locations.BossBar;
import com.exorath.game.api.hud.locations.Subtitle;
import com.exorath.game.api.hud.locations.Title;
import com.exorath.game.api.hud.locations.scoreboard.Scoreboard;
import com.exorath.game.api.player.GamePlayer;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by TOON on 8/9/2015.
 */
public abstract class HUDLocation extends BukkitRunnable {

    @SuppressWarnings("unchecked")
    public static Class<? extends HUDLocation> LOCATIONS[] = new Class[] { ActionBar.class, BossBar.class,
            Subtitle.class, Title.class, Scoreboard.class };
    protected boolean active = true;
    protected GamePlayer player;
    private int currentSequence = Integer.MIN_VALUE;//This is for HUDText's with the same priority (FIFO behaviour!)

    public HUDLocation(GamePlayer player) {
        this.player = player;
        this.runTaskTimer(GameAPI.getInstance(), 0, 1);
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
