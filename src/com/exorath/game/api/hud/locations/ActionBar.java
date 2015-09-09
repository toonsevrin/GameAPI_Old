package com.exorath.game.api.hud.locations;

import com.exorath.game.GameAPI;
import com.exorath.game.api.hud.HUDDisplay;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.hud.actionbar.ActionBarBase;
import com.exorath.game.lib.hud.bossbar.BossBarAPI;

/**
 * Created by TOON on 8/11/2015.
 */
public class ActionBar extends HUDDisplay {

    public ActionBar(GamePlayer player) {
        super(player, 64);
    }

    @Override
    public void displayText(HUDText text) {
        GameAPI.printConsole("Displaying text: " + text);
        ActionBarBase.send(player.getBukkitPlayer(), text.getDisplayText());
    }

    @Override
    public void removeCurrent() {

    }

    public void setHealth(float health) {
        BossBarAPI.setHealth(player.getBukkitPlayer(), health);
    }
}
