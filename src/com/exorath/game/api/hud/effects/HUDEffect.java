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
        if (text != null)
            text.setDisplayText(newText);
    }

    public void setHUDText(HUDText text) {
        this.text = text;
    }

    public String getText() {
        if (text == null)
            return "";
        return text.getText();
    }

    public abstract String getDisplayText();

    @Override
    public abstract HUDEffect clone();
}
