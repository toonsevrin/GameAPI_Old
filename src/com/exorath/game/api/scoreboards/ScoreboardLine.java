package com.exorath.game.api.scoreboards;

import com.exorath.game.api.scoreboards.displayeffects.DisplayEffect;
import com.exorath.game.api.scoreboards.displayeffects.NoneEffect;

/**
 * Created by TOON on 7/3/2015.
 */
public class ScoreboardLine {
    private Scoreboard scoreboard;
    private String key;
    private String content;
    private String displayContent;
    private int priority;
    private DisplayEffect displayEffect;
    private boolean visible = true;
    public ScoreboardLine(Scoreboard scoreboard, String key, String content, int priority, DisplayEffect displayEffect, boolean visible){
        this.scoreboard = scoreboard;
        this.key = key;
        this.content = content;
        this.priority = priority;
        displayEffect.setLine(this);
        this.visible = visible;
    }

    /* Getters & Setters */

    public Scoreboard getScoreboard(){
        return scoreboard;
    }
    public int getPriority(){
        return priority;
    }
    public void setPriority(int priority){
        this.priority = priority;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content) {
        if(this.content == content) return;
        this.content = content;
        scoreboard.addUpdated(this);
    }

    /**
     * This content can be modified by the displayEffect
     * @return
     */
    public String getDisplayContent(){
        return displayContent == null? content: displayContent;
    }
    public void setDisplayContent(String displayContent){
        this.displayContent = displayContent;
    }
    public DisplayEffect getDisplayEffect() {
        return displayEffect;
    }
    public void setDisplayEffect(DisplayEffect effect){
        this.displayEffect = displayEffect;
    }
    public boolean isVisible(){ return visible;};
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public enum Priority{
        HIGHEST,
        HIGH,
        GAME_API,
        MEDIUM,
        LOW,
        LOWER,
        LOWEST;
    }
}
