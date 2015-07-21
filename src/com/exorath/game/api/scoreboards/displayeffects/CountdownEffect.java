package com.exorath.game.api.scoreboards.displayeffects;

import com.exorath.game.api.scoreboards.ScoreboardLine;
import com.exorath.game.lib.util.Countdown;

/**
 * Created by TOON on 7/20/2015.
 */
public abstract class CountdownEffect extends Countdown implements DisplayEffect{
    private ScoreboardLine line;
    public CountdownEffect(int waitTime){
        super(waitTime);
    }
    @Override
    public void setLine(ScoreboardLine line){
        this.line = line;
    }
    /**
     * Count 1 down every tick from the wait time
     */
    @Override
    public void tick(){
        countdown();
    }

    /**
     * Update the variables and display the text when counted down to 0
     */
    @Override
    public void run(){
        updateVariables();
        updateText();
    }

    /**
     * This method updates the variables
     */
    public abstract void updateVariables();

    /**
     * @return Line this effect is applied to
     */
    public ScoreboardLine getLine(){
        return line;
    }
}
