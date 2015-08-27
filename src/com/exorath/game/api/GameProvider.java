package com.exorath.game.api;

/**
 * Created by TOON on 8/23/2015.
 */
public interface GameProvider {

    void start();

    Game create();

    String getName();

}
