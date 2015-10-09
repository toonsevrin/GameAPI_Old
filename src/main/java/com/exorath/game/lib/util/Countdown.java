package com.exorath.game.lib.util;

/**
 * Created by Toon Sevrin on 7/20/2015.
 * This class counts 1 down every time it's updated and runs the run method at
 * 0, then it resets to max.
 */
public abstract class Countdown {

    private int start;
    private int current;

    public Countdown(int start) {
        this.start = start;
        current = start;
    }

    public void countdown() {
        current--;
        if (current == 0)
            run();
        else if (current < 0)
            current = start;
    }

    public abstract void run();

    /* Getters and Setters */
    public int getStart() {
        return start;
    }

    public int getCurrent() {
        return current;
    }
}
