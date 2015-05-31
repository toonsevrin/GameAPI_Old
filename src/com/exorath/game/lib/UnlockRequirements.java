package com.exorath.game.lib;

import com.exorath.game.api.player.GamePlayer;

/**
 * Created by too on 23/05/2015.
 * Minimum Requirements to make a certain unlock
 */
public class UnlockRequirements {
    
    private int honorPoints = 0;
    private int credits = 0;
    private Rank minRank;
    
    public boolean canUnlock( GamePlayer player ) {
        // TODO: Check values
        return true;
    }
    
    public void unlock( GamePlayer player ) {
        // TODO: Add content
    }
    
    public boolean isUnlocked( GamePlayer player ) {
        // TODO
        return false;
    }
    
    public void setHonorPoints( int honorPoints ) {
        this.honorPoints = honorPoints;
    }
    
    public int getHonorPoints() {
        return this.honorPoints;
    }
    
    public void setCredits( int credits ) {
        this.credits = credits;
    }
    
    public int getCredits() {
        return this.credits;
    }
    
    public void setMinRank( Rank minRank ) {
        this.minRank = minRank;
    }
    
    public Rank getMinRank() {
        return this.minRank;
    }
    
}
