package com.exorath.game.api.hud;

import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.scoreboards.ScoreboardLine;

import java.util.PriorityQueue;

/**
 * Created by TOON on 8/9/2015.
 */
public abstract class HUDLocation implements Runnable{
    protected boolean active;
    protected GamePlayer player;
    private int currentSequence = Integer.MIN_VALUE; //This is for HUDText's with the same priority (FIFO behaviour!)
    private PriorityQueue<HUDText> texts = new PriorityQueue<HUDText>();

    public HUDLocation(GamePlayer player){
        this.player = player;
    }
    public boolean isActive(){
        return active;
    }
    public GamePlayer getPlayer(){
        return player;
    }
    public abstract void setActive(boolean active);
    public abstract void updated(HUDText text);
    public PriorityQueue<HUDText> getTexts(){
        return texts;
    }

    /**
     * @return currentSequence + 1 and adds 1 to the currentSequence
     */
    protected int getNewSequence(){
        if(currentSequence == Integer.MAX_VALUE)
            currentSequence = Integer.MIN_VALUE;

        currentSequence++;
        return currentSequence;
    }
}
