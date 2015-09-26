package com.exorath.game.api.type.minigame.countdown;

import com.exorath.game.api.Game;

/**
 * Created by TOON on 9/3/2015.
 */
public class FinishFrame extends CountdownFrame {

    public FinishFrame(MinigameCountdown minigameCountdown) {
        super(minigameCountdown, 0);
    }

    @Override
    public void display(Game game) {
        getMinigameCountdown().startGame();
    }

    @Override
    public void finish(Game game) {

    }
}
