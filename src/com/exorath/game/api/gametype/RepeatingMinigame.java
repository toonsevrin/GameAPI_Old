package com.exorath.game.api.gametype;

import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.exorath.game.api.GameProperty;
import com.exorath.game.api.GameState;
import com.exorath.game.api.Property;
import com.exorath.game.api.events.GameStateChangedEvent;

/**
 * Created by too on 27/05/2015.
 * A minigame that keeps repeating when finished
 */
public class RepeatingMinigame extends Minigame {
    
    public RepeatingMinigame() {
        
    }
    
    @EventHandler
    public void onGameStop( GameStateChangedEvent event ) {
        if ( event.getNewState() != GameState.RESETTING && event.getNewState() != GameState.RESTARTING ) {
            return;
        }
        if ( this.getDelay() == 0 ) {
            this.startGame();
            return;
        }
        new DelayedStartTask().runTaskLater( GameAPI.getInstance(), this.getDelay() * 20 );
    }
    
    public int getDelay() {
        return this.getProperties().as( RepeatingGameProperty.DELAY, int.class );
    }
    
    public void setDelay( int delay ) {
        this.getProperties().set( RepeatingGameProperty.DELAY, delay );
    }
    
    public static class RepeatingGameProperty extends GameProperty {
        public static final Property DELAY = Property.get( "delay", "Seconds between two games, 0 if none", 45 );
    }
    
    private class DelayedStartTask extends BukkitRunnable {
        @Override
        public void run() {
            startGame();
        }
    }
    
}
