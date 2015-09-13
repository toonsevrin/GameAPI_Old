package com.exorath.game.api.hud.locations;

import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.exorath.game.api.hud.HUDDisplay;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.hud.actionbar.ActionBarBase;

/**
 * Created by TOON on 8/11/2015.
 * REPEATER CLASS KEEPS RUNNING DURING CACHING -_- REMOVE CACHING PLEZ, PLEZ, PLEZ
 */
public class ActionBar extends HUDDisplay {

    public ActionBar(GamePlayer player) {
        super(player, 64);
        new Repeater().runTaskTimer(GameAPI.getInstance(),40,40);
    }

    @Override
    public void displayText(HUDText text) {
        display(text.getDisplayText());
    }

    @Override
    public void removeCurrent() {

    }
    private void display(String text){
        ActionBarBase.send(player.getBukkitPlayer(), text);
    }
    private class Repeater extends BukkitRunnable{
        @Override
        public void run(){
            if(player == null)
                cancel();
            if(!player.isOnline())
                return;
            if(getCurrentText() != null)
                display(getCurrentText().getDisplayText());
        }
    }
}
