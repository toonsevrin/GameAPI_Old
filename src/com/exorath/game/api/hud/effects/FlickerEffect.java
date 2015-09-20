package com.exorath.game.api.hud.effects;

import com.exorath.game.GameAPI;
import org.bukkit.ChatColor;

/**
 * Created by TOON on 8/11/2015.
 */
public class FlickerEffect extends IntervalEffect {

    boolean visible = true;
    ChatColor replaceColor;

    public FlickerEffect(int interval) {
        super(interval);
    }

    public FlickerEffect(int interval, ChatColor replaceColor) {
        super(interval);
        this.replaceColor = replaceColor;
    }

    @Override
    public void run() {
        GameAPI.printConsole("Flicker effect ran!");
        visible = !visible;
        setText(getDisplayText());
    }

    @Override
    public String getDisplayText() {

        GameAPI.printConsole("Flicker effect display text updated!");
        if (visible) {
            return getText();
        }
        return replaceColor != null ? replaceColor + ChatColor.stripColor(getText()) : "";
    }

    @Override
    public HUDEffect clone() {
        FlickerEffect effect = new FlickerEffect(getInterval());
        effect.replaceColor = replaceColor;
        effect.visible = visible;
        return effect;
    }
}
