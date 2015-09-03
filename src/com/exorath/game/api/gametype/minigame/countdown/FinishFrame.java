package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 9/3/2015.
 */
public class FinishFrame extends CountdownFrame{
    public FinishFrame(MinigameCountdown minigameCountdown){
        super(minigameCountdown, 0);
    }
    @Override
    public void display(GamePlayer player) {
        getMinigameCountdown().finish();
    }

    @Override
    public void finish() {

    }
}
