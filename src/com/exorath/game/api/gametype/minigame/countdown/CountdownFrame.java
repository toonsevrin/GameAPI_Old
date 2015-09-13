package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.api.Game;

/**
 * Created by TOON on 9/2/2015.
 */
public abstract class CountdownFrame {

    private int delay;
    private MinigameCountdown minigameCountdown;

    public CountdownFrame(MinigameCountdown minigameCountdown, int delay) {
        this.minigameCountdown = minigameCountdown;
        this.delay = delay;
    }

    public abstract void display(Game game);

    public abstract void finish(Game game);

    public int getDelay() {
        return delay;
    }

    public MinigameCountdown getMinigameCountdown() {
        return minigameCountdown;
    }
}
