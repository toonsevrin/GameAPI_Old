package com.exorath.game.api.hud.effects;

import org.bukkit.ChatColor;

/**
 * Created by TOON on 8/11/2015.
 */
public class RainbowEffect extends IntervalEffect {

    private int color = 0;
    private static final ChatColor[] colors = new ChatColor[] { ChatColor.DARK_RED, ChatColor.RED, ChatColor.GOLD,
            ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN, ChatColor.AQUA, ChatColor.DARK_AQUA,
            ChatColor.BLUE, ChatColor.DARK_BLUE, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE };
    private ChatColor extra;

    public RainbowEffect(int interval) {
        super(interval);
    }

    public RainbowEffect(int interval, ChatColor extra) {
        super(interval);
        this.extra = extra;
    }

    @Override
    public void run() {
        color++;
        if (color == colors.length)
            color = 0;
        setText(getDisplayText());
    }

    @Override
    public String getDisplayText() {
        int tColor = color;
        String text = getText();
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            sb.append(colors[tColor % colors.length].toString());
            if (extra != null)
                sb.append(extra.toString());
            sb.append(c);
            tColor++;
        }
        return sb.toString();
    }

    @Override
    public HUDEffect clone() {
        RainbowEffect effect = new RainbowEffect(getInterval());
        effect.color = color;
        return effect;
    }
}
