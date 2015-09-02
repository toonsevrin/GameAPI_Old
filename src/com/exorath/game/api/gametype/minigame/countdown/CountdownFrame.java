package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.api.Game;
import com.exorath.game.api.gametype.minigame.Minigame;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 9/2/2015.
 */
public abstract class CountdownFrame {
    private int delay;
    private MinigameCountdown minigameCountdown;
    public CountdownFrame(MinigameCountdown minigameCountdown, int delay){
        this.minigameCountdown = minigameCountdown;
        this.delay = delay;
    }

    public abstract void display(GamePlayer player);
    public abstract void finish();
    public int getDelay() {
        return delay;
    }
    public MinigameCountdown getMinigameCountdown() {
        return minigameCountdown;
    }
}
