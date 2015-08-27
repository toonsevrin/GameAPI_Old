package com.exorath.game.api;

/**
 * Created by TOON on 8/23/2015.
 */
public interface GameProvider {

    void init();

    Game create();

    String getName();

}
