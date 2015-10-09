package com.exorath.game.lib.util;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by TOON on 9/1/2015.
 */
public abstract class BukkitCountdown {

    private CountdownTask countdownTask;

    private int ticks;

    public BukkitCountdown(int ticks) {
        this.ticks = ticks;
        countdownTask = new CountdownTask(ticks);
    }

    public void interupt() {
        if (countdownTask != null)
            countdownTask.cancel();
    }

    public abstract void onFinish();

    private class CountdownTask extends BukkitRunnable {

        public CountdownTask(int ticks) {
            countdownTask = new CountdownTask(ticks);
        }

        @Override
        public void run() {
            ticks--;
            if (ticks <= 0) {
                cancel();
                onFinish();
            }
        }
    }
}
