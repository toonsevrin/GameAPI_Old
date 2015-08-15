package com.exorath.game.api.hud.effects;

import org.bukkit.ChatColor;

/**
 * Created by TOON on 8/11/2015.
 */
public class RainbowEffect extends IntervalEffect{
    private int color = 0;
    private static final ChatColor[] colors = new ChatColor[]{ChatColor.DARK_RED, ChatColor.RED, ChatColor.GOLD, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN, ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.BLUE, ChatColor.DARK_BLUE, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE};
    public RainbowEffect(int interval){
        super(interval);
    }
    @Override
    public void run(){
        color++;
        if(color == colors.length)
            color = 0;
        setText(getDisplayText());
    }

    @Override
    public String getDisplayText() {
        int tColor = color;
        String text = getText();
        StringBuilder sb = new StringBuilder();
        for(char c : text.toCharArray()){
            sb.append(colors[tColor % colors.length].toString() + c);
            tColor++;
        }
        return sb.toString();
    }
}
