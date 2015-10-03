package com.exorath.game.api.type.minigame.countdown;

import com.exorath.game.api.Game;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;

/**
 * Created by TOON on 9/2/2015.
 */
public class SubtitleCountdownFrame extends CountdownFrame {

    private String text;

    public SubtitleCountdownFrame(MinigameCountdown minigameCountdown, String text, int delay) {
        super(minigameCountdown, delay);
        this.text = text;
    }

    @Override
    public void display(Game game) {
        HUDManager.PublicHUD publicHUD = game.getManager(HUDManager.class).getPublicHUD();
        if (publicHUD.containsSubtitle("gapi_countdownsub"))
            publicHUD.updateSubtitle("gapi_countdownsub", text);
        else
            publicHUD.addSubtitle("gapi_countdownsub", new HUDText(text, HUDPriority.HIGH.get()),true);
    }

    @Override
    public void finish(Game game) {
        HUDManager.PublicHUD publicHUD = game.getManager(HUDManager.class).getPublicHUD();
        publicHUD.removeSubtitle("gapi_countdownsub");
    }
}
