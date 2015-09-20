package com.exorath.game.api.hud.effects;

/**
 * Created by TOON on 8/11/2015.
 */
public class ScrollEffect extends IntervalEffect {

    int startIndex = 0;
    int length = 16;

    public ScrollEffect(int interval, int length) {
        super(interval);
        this.length = length;
    }

    @Override
    public void run() {
        startIndex++;

        setText(getDisplayText());
    }

    @Override
    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        char[] chars = getText().toCharArray();
        for (int i = 0; i < length; i++) {
            sb.append(chars[(startIndex + i) % chars.length]);
        }
        return sb.toString();
    }

    @Override
    public HUDEffect clone() {
        ScrollEffect effect = new ScrollEffect(getInterval(), length);
        effect.startIndex = startIndex;
        return effect;
    }
}
