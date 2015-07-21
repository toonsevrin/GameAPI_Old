package com.exorath.game.api.scoreboards.displayeffects;

import com.exorath.game.api.scoreboards.ScoreboardLine;

/**
 * Created by TOON on 7/20/2015.
 * Default effect
 */
public class NoneEffect implements DisplayEffect{
    ScoreboardLine line;
    @Override
    public void setLine(ScoreboardLine line){
        this.line = line;
    }
    @Override
    public void tick(){}

    @Override
    public void updateText() {
        line.setDisplayContent(line.getContent());
    }
}
