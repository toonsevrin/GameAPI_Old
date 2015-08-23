package com.exorath.game.api.scoreboards.displayeffects.animated;

import com.exorath.game.api.scoreboards.ScoreboardLine;
import com.exorath.game.api.scoreboards.displayeffects.CountdownEffect;
import net.md_5.bungee.api.ChatColor;

/**
 * Created by TOON on 7/20/2015.
 * The rainbow effect scrolls through the colors over time with a different color on each character
 */
public class RainbowEffect extends CountdownEffect {
    private int first = 0;
    private ChatColor[] colors = new ChatColor[]{ChatColor.DARK_RED, ChatColor.RED, ChatColor.GOLD, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN, ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.BLUE, ChatColor.DARK_BLUE, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE};
    public RainbowEffect(int moveDelay){
        super(moveDelay);
    }

    @Override
    public void updateVariables(){
        first = (first + 1) % colors.length;
    }
    @Override
    public void updateText(){
        StringBuilder sb = new StringBuilder();
        String content =  ChatColor.stripColor(getLine().getContent());
        for (int pos = 0; pos < content.length(); pos++){
            char character = content.charAt(pos);
            sb.append(colors[(first + pos) % colors.length]);
        }
        getLine().setDisplayContent(sb.toString());
    }
}
