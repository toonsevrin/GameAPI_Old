package com.exorath.game.api.gametypes;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.GameProperty;
import com.exorath.game.api.Property;
import com.exorath.game.api.events.GameStopEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by too on 27/05/2015.
 * A minigame that keeps repeating when finished
 */
public class RepeatingMinigame extends Game {

    public RepeatingMinigame(){

    }
    public void startGame(){
        super.startGame();
    }
    @EventHandler
    public void onGameStop(GameStopEvent event){
        if(getDelay() == 0){
            startGame();
            return;
        }
        new DelayedStartTask().runTaskLater(GameAPI.getInstance(), getDelay() * 20);
    }
    public int getDelay(){
        return getProperties().as(RepeatingGameProperty.DELAY, int.class);
    }

    public void setDelay(int delay){
        getProperties().set(RepeatingGameProperty.DELAY, delay);
    }

    public static class RepeatingGameProperty extends GameProperty {
        public static final Property
                DELAY = Property.get("delay", "Seconds between two games, 0 if none", 45);
    }

    private class DelayedStartTask extends BukkitRunnable{
        @Override
        public  void run(){
            startGame();
        }
    }
}
