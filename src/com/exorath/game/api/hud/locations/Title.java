package com.exorath.game.api.hud.locations;

import com.exorath.game.api.hud.HUDDisplay;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.hud.bossBar.BossBarAPI;
import com.exorath.game.lib.hud.title.TitleBase;

/**
 * Created by TOON on 8/11/2015.
 */
public class Title extends Title_SubTitleBase {
    public Title(GamePlayer player){
        super(player, 32);
        otherLocation = null;//TODO: Set this
    }

    @Override
    public void removeSelf() {
        TitleBase.sendTitle(player.getBukkitPlayer(), getJSON(""));
    }
    @Override
    public void sendWithFadeIn(HUDText text){
        TitleBase.sendTitle(player.getBukkitPlayer(), 20, Integer.MAX_VALUE / 10, 0, getJSON(text.getDisplayText()));
    }
    @Override
    public void send(HUDText text){
        TitleBase.sendSubTitle(player.getBukkitPlayer(), getJSON(text.getDisplayText()));
    }

}
