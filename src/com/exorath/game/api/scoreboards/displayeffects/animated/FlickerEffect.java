package com.exorath.game.api.scoreboards.displayeffects.animated;

import com.exorath.game.api.scoreboards.ScoreboardLine;
import com.exorath.game.api.scoreboards.displayeffects.CountdownEffect;
import net.md_5.bungee.api.ChatColor;

/**
 * Created by TOON on 7/20/2015.
 * The flicker effect flickers the display content between bold and not bold
 */
public class FlickerEffect extends CountdownEffect {
    private boolean bold = true;
    public FlickerEffect(ScoreboardLine line, int flickerDelay){
        super(line, flickerDelay);
    }
    @Override
    public void updateVariables(){
        bold = !bold;//Reverse visibility
    }
    @Override
    public void updateText(){
        String content = getLine().getContent().replace("§l","");
        if(bold){
            getLine().setDisplayContent(ChatColor.BOLD + getLine().getContent());
        }else{
            getLine().setDisplayContent(getLine().getContent());
        }
    }
}
