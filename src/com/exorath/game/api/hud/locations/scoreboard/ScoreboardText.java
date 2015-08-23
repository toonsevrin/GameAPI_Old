package com.exorath.game.api.hud.locations.scoreboard;

import com.exorath.game.api.hud.HUDDisplay;
import com.exorath.game.api.hud.HUDLocation;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.lib.hud.scoreboard.ScoreboardBase;
import com.exorath.game.lib.hud.scoreboard.SpigboardEntry;

/**
 * Created by TOON on 8/12/2015.
 */
public class ScoreboardText extends HUDText {
    private SpigboardEntry entry;
    private boolean textUpdated = false;
    private boolean priorityUpdated = false;
    public ScoreboardText(String text, HUDPriority priority){
        super(text,priority);
    }
    protected void setEntry(SpigboardEntry entry){
        this.entry = entry;
    }
    protected SpigboardEntry getEntry(){
        return entry;
    }
    @Override
    public void setText(String text){
        textUpdated = true;
        priorityUpdated = false;
        super.setText(text);
    }
    @Override
    public void setPriority(HUDPriority priority){
        textUpdated = false;
        priorityUpdated = true;
        super.setPriority(priority);
    }
    @Override
    public void setLocation(HUDLocation loc){
        if(!(loc instanceof Scoreboard))
            return;
        this.setLocation(loc);
        Scoreboard sb = (Scoreboard) loc;
        ScoreboardBase base = sb.getScoreboard();
    }
    @Override
    public void updateLocation(){
        if(getLocation() == null)
            return;
        Scoreboard sb = (Scoreboard) getLocation();
        sb.updated(this);
    }
    protected boolean isTextUpdated(){
        return textUpdated;
    }
    protected boolean isPriorityUpdated(){
        return priorityUpdated;
    }
}