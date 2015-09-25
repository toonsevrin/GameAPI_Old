package com.exorath.game.api.hud.effects;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;

/**
 * Created by TOON on 8/11/2015.
 */
public class RainbowFlickerEffect extends IntervalEffect {

    private static final ChatColor[] colors = new ChatColor[] { ChatColor.DARK_RED, ChatColor.RED, ChatColor.GOLD,
            ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN, ChatColor.AQUA, ChatColor.DARK_AQUA,
            ChatColor.BLUE, ChatColor.DARK_BLUE, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE };
    private int index = 0;

    public RainbowFlickerEffect(int interval) {
        super(interval);
    }

    @Override
    public void run() {
        index++;
        if (index == colors.length)
            index = 0;
        setText(getDisplayText());
    }

    private ChatColor[] keep = new ChatColor[] { ChatColor.BOLD, ChatColor.UNDERLINE, ChatColor.ITALIC, ChatColor.MAGIC };

    @Override
    public String getDisplayText() {
        String text = getText();
        for (ChatColor color : ChatColor.values()) {
            if (ArrayUtils.contains(keep, color))
                continue;
            text = text.replaceAll(color.toString(), "");
        }
        return colors[index] + text;
    }

    @Override
    public HUDEffect clone() {
        RainbowFlickerEffect effect = new RainbowFlickerEffect(getInterval());
        effect.index = index;
        return effect;
    }
}
