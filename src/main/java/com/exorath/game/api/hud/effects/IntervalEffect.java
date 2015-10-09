package com.exorath.game.api.hud.effects;

/**
 * Created by TOON on 8/11/2015.
 */
public abstract class IntervalEffect extends HUDEffect {

    private int current = 0;
    private int interval = 1;

    public IntervalEffect(int interval) {
        this.interval = interval;
        if (interval < 1)
            interval = 1;
    }

    @Override
    public void tick() {
        current++;
        if (current == interval) {
            current = 0;
            run();
        }
    }

    public int getInterval() {
        return interval;
    }

    public abstract void run();
}
