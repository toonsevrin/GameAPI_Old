package com.exorath.game.api.hud.effects;

import com.exorath.game.api.hud.HUDText;

/**
 * Created by TOON on 8/11/2015.
 */
public abstract class HUDEffect {

    private HUDText text;

    public void tick() {

    }

    public void setText(String newText) {
        text.setDisplayText(newText);
    }

    public String getText() {
        return text.getText();
    }

    public abstract String getDisplayText();
}
