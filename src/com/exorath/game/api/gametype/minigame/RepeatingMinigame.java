package com.exorath.game.api.gametype.minigame;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.exorath.game.api.GameState;
import com.exorath.game.api.Property;

/**
 * Created by Toon Sevrin on 27/05/2015.
 * TODO: Requires more foundation before it can be continued
 */
public abstract class RepeatingMinigame extends Minigame {
    public static final Property FINISHING_TIME = Property.get( "finishtime", "Time before going from stage 4. FINISHING to stage 1. PREGAME", 7 );

    public RepeatingMinigame() {

    }

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        if ( this.getState() == GameState.WAITING && this.getPlayerCount() >= this.getProperties().as(Minigame.MIN_PLAYERS, Integer.class ) ) { //Enough players to start + waiting to start
            this.startCountdown();
        }
    }

    public int getDelay() {
        return this.getProperties().as( RepeatingMinigame.REPEAT_DELAY, int.class );
    }

    public void setDelay( int delay ) {
        this.getProperties().set( RepeatingMinigame.REPEAT_DELAY, delay );
    }

    /*============- STAGES -============*/

    /*=== START PREGAME ===*/

    private boolean countingDown = false;

    private void startPregame() {
        this.setState( GameState.WAITING );
    }

    /**
     * Starts counting down to start of game
     */
    private void startCountdown() {
        if ( this.countingDown ) {
            return;
        }
        this.countingDown = true;
        new CountdownTask().runTaskTimer( GameAPI.getInstance(), 0, 20 );//Counts down every second
    }

    /**
     * Cancels the counting down to start
     */
    private void cancelCountdown() {
        if ( !this.countingDown ) {
            return;
        }

        this.countingDown = false;
    }

    /**
     * While counting down with enough players this method is ran every second
     *
     * @param remainingSeconds
     *            The amount of seconds remaining
     */
    private void countDown( int remainingSeconds ) {
        GameAPI.printConsole( "Game starting in: " + remainingSeconds );//Must be replaced with some visuals
        if ( remainingSeconds == 0 ) {
            this.startFinishing();
            this.startResetting(); //Go to stage 2 or immediately to stage 3 :)
        }
    }

    /*
     * This task counts down every second towards the start of the game while there are enough players in the game
     */
    private class CountdownTask extends BukkitRunnable {
        private int countdownSeconds;

        public CountdownTask() {
            this.countdownSeconds = RepeatingMinigame.this.getProperties().as(Minigame.MIN_PLAYERS, Integer.class );
        }

        @Override
        public void run() { //This method is ran every tick
            if ( !RepeatingMinigame.this.countingDown ) {
                this.cancel();
                return;
            }
            if ( !RepeatingMinigame.this.hasMinPlayers() ) {
                RepeatingMinigame.this.cancelCountdown();
                this.cancel();
                return;
            }
            this.countdownSeconds--;
            RepeatingMinigame.this.countDown( this.countdownSeconds );
            if ( this.countdownSeconds == 0 ) {
                this.cancel();
            }
        }
    }

    /*=== END PREGAME ===*/
    /*=== START RESETTING ===*/

    private void startResetting() {
        //if(Property.as(RepeatingMinigame.RESET, Boolean.class){
        this.setState( GameState.RESETTING );
        GameAPI.printConsole( "Resetting repeating minigame." );
        //Reset stuff
        //}
        this.startPlaying();
    }

    /*=== END RESETTING ===*/
    /*=== START PLAYING ===*/

    private void startPlaying() {
        GameAPI.printConsole( "Started playing repeating minigame." );
        this.setState( GameState.STARTING );
    }

    /*=== END PLAYING ===*/
    /*=== START FINISHING ===*/

    private void startFinishing() {
        GameAPI.printConsole( "Finishing repeating minigame (Handing out rewards...)." );
        this.setState( GameState.FINISHING );
        this.finish();
        Bukkit.getScheduler().scheduleSyncDelayedTask( GameAPI.getInstance(), ( ) -> RepeatingMinigame.this.startPregame(),
                this.getProperties().as( RepeatingMinigame.FINISHING_TIME, Integer.class ) * 20 );
        this.startPregame(); //Go back to stage 1. PREGAME
    }

    /**
     * This is called at the end of the game. When called, it should reset the game to default
     * settings (as if the server had just started).
     */
    public abstract void finish();

    /*=== END FINISHING ===*/
    /*============- END STAGES -============*/
}
