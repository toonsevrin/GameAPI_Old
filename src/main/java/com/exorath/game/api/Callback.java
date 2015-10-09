package com.exorath.game.api;

/**
 * @author Nick Robson
 */
@FunctionalInterface
public interface Callback<T> {

    void run(T obj);

}
