package com.exorath.game.api.scoreboards.displayeffects.animated;

import com.exorath.game.api.scoreboards.ScoreboardLine;
import com.exorath.game.api.scoreboards.displayeffects.CountdownEffect;
import net.md_5.bungee.api.ChatColor;

/**
 * Created by TOON on 7/20/2015.
 * The scroll effect scrolls over the letters
 */
public class ScrollEffect extends CountdownEffect {
    private int first = 0;
    public ScrollEffect(int moveDelay){
        super(moveDelay);
    }

    @Override
    public void updateVariables(){
        first = (first + 1) % getLine().getContent().length();
    }
    @Override
    public void updateText() {
        first %= getLine().getContent().length();
        //TODO: Add the scroll effect with color support.
    }
}
