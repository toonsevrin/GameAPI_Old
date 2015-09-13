package com.exorath.game.api.hud.locations;

import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.hud.title.TitleBase;

/**
 * Created by TOON on 8/11/2015.
 */
public class Subtitle extends Title_SubTitleBase {

    public Subtitle(GamePlayer player) {
        super(player, 48);
    }

    @Override
    public void removeSelf() {
        TitleBase.sendSubTitle(player.getBukkitPlayer(), getJSON(""));
    }

    @Override
    public void sendWithFadeIn(HUDText text) {
        if(player.getHud().getTitle().getCurrentText() == null)
            TitleBase.sendTitle(player.getBukkitPlayer(), "");
        TitleBase.sendSubTitle(player.getBukkitPlayer(), 20, LONG_STAY_TIME, 0, getJSON(text.getDisplayText()));
    }

    @Override
    public void send(HUDText text) {
        if(player.getHud().getTitle().getCurrentText() == null)
            TitleBase.sendTitle(player.getBukkitPlayer(), "");
        TitleBase.sendSubTitle(player.getBukkitPlayer(), 0, LONG_STAY_TIME,0, getJSON(text.getDisplayText()));
    }
}
