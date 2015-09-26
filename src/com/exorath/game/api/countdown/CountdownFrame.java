package com.exorath.game.api.countdown;

import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public interface CountdownFrame {

    int getDuration();

    void start(GamePlayer player);

    void display(GamePlayer player);

    void end(GamePlayer player);

}
