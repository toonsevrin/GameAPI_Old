package com.exorath.game.api.hud.locations;

import com.exorath.game.api.hud.HUDDisplay;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.hud.bossBar.BossBarAPI;

/**
 * Created by TOON on 8/9/2015.
 */
public class BossBar extends HUDDisplay {
    public BossBar(GamePlayer player){
        super(player, 64);
    }
    @Override
    public void displayText(HUDText text) {
        BossBarAPI.setMessage(player.getBukkitPlayer(), text.getDisplayText());
    }

    @Override
    public void removeCurrent() {
        if(BossBarAPI.hasBar(player.getBukkitPlayer()))
            BossBarAPI.removeBar(player.getBukkitPlayer());
    }
    public void setHealth(float health){
        BossBarAPI.setHealth(player.getBukkitPlayer(), health);
    }
}
