package com.exorath.game.api;


/**
 * @author Nick Robson
 */
public interface Callback<T> {

    void run(T obj);

}
