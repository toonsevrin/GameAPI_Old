package com.exorath.game.api;

/**
 * Created by TOON on 8/25/2015.
 */
public interface Manager {

    public default void onCreate(){}
    public default void onDestroy(){}
}
