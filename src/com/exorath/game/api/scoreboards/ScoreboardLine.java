package com.exorath.game.api.scoreboards;

/**
 * Created by TOON on 7/3/2015.
 */
public class ScoreboardLine {
    private Scoreboard scoreboard;
    private int priority;
    private String content;

    public ScoreboardLine(Scoreboard scoreboard, int priority){
        this.scoreboard = scoreboard;
        this.priority = priority;
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
        this.content = content;
    }
}
