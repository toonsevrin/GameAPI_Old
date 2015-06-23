package com.exorath.game.api.gametype;

import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.exorath.game.api.GameState;
import com.exorath.game.api.Property;
import com.exorath.game.api.events.GameStateChangedEvent;

/**
 * Created by too on 27/05/2015.
 * A minigame that keeps repeating when finished
 */
public class RepeatingMinigame extends Minigame {
    
    public static final Property REPEAT_DELAY = Property.get( "repeating.delay", "Time in seconds before the game repeats", 60 );
    
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
        return this.getProperties().as( RepeatingMinigame.REPEAT_DELAY, int.class );
    }
    
    public void setDelay( int delay ) {
        this.getProperties().set( RepeatingMinigame.REPEAT_DELAY, delay );
    }
    
    private class DelayedStartTask extends BukkitRunnable {
        @Override
        public void run() {
            RepeatingMinigame.this.startGame();
        }
    }
    
}
