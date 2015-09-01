package com.exorath.game.lib.util;

import com.exorath.game.api.player.GamePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

/**
 * Created by TOON on 9/1/2015.
 */
public abstract class BukkitCountdown {
    private CountdownTask countdownTask;
    private Collection<GamePlayer> players;

    private int ticks;

    public BukkitCountdown(int ticks, Collection<GamePlayer> players){
        this.ticks = ticks;
        this.players = players;
        countdownTask = new CountdownTask(ticks);
    }
    public void interupt(){
        if(countdownTask != null)
            countdownTask.cancel();
    }
    public abstract void onFinish();
    private class CountdownTask extends BukkitRunnable{
        public CountdownTask(int ticks){
            countdownTask = new CountdownTask(ticks);
        }
        @Override
        public void run(){
            ticks--;
            for(GamePlayer player : players){

            }
            if(ticks <= 0){
                this.cancel();
                onFinish();
            }
        }
    }
}
