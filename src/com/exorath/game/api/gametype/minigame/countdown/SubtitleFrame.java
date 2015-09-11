package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.hud.locations.ActionBar;
import com.exorath.game.api.hud.locations.Subtitle;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 9/2/2015.
 */
public class SubtitleFrame extends CountdownFrame {

    private String text;

    public SubtitleFrame(MinigameCountdown minigameCountdown, String text, int delay) {
        super(minigameCountdown, delay);
        this.text = text;
    }

    @Override
    public void display(Game game) {
        HUDManager.PublicHUD publicHUD = game.getManager(HUDManager.class).getPublicHUD();
        if (publicHUD.containsSubtitle("gapi_countdown"))
            publicHUD.updateSubtitle("gapi_countdown",text);
        else
            publicHUD.addSubtitle("gapi_countdown", new HUDText(text, HUDPriority.HIGH));
    }

    @Override
    public void finish() {
        GameAPI.getOnlinePlayers().forEach(p -> p.getHud().getSubtitle().removeText("gapi_countdown"));
    }
}
