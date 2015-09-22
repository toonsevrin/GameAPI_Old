package com.exorath.game.api.hud.effects;

import com.exorath.game.GameAPI;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;

/**
 * Created by TOON on 8/11/2015.
 */
public class FlickerEffect extends IntervalEffect {

    boolean visible = true;
    ChatColor replaceColor;

    public FlickerEffect(int interval, ChatColor replaceColor) {
        super(interval);
        this.replaceColor = replaceColor;
    }

    @Override
    public void run() {
        visible = !visible;
        setText(getDisplayText());
    }

    private ChatColor[] keep = new ChatColor[]{ChatColor.BOLD,ChatColor.UNDERLINE,ChatColor.ITALIC,ChatColor.MAGIC};
    @Override
    public String getDisplayText() {
        if (visible)
            return getText();
        String text = getText();
        for(ChatColor color : ChatColor.values()){
            if(ArrayUtils.contains(keep, color))
                continue;
            text = text.replaceAll(color.toString(), "");
        }
        return replaceColor + text;
    }

    @Override
    public HUDEffect clone() {
        FlickerEffect effect = new FlickerEffect(getInterval(), replaceColor);
        effect.visible = visible;
        return effect;
    }
}
