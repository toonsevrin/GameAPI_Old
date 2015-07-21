package com.exorath.game.api.scoreboards.displayeffects;

import com.exorath.game.api.scoreboards.ScoreboardLine;

/**
 * Created by TOON on 7/20/2015.
 */
public interface DisplayEffect {

    public  void tick();
    public  void updateText();
    public void setLine(ScoreboardLine line);
}
