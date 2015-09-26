package com.exorath.game.api.type.minigame.countdown;

import com.exorath.game.api.Game;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;
import org.bukkit.Sound;

/**
 * Created by TOON on 9/11/2015.
 */
public class TitleSubtitleSoundFrame extends SoundCountdownFrame {

    private String title;

    public TitleSubtitleSoundFrame(MinigameCountdown minigameCountdown, String subtitle, String title, int delay, Sound sound, float pitch,
            float vol) {
        super(minigameCountdown, subtitle, delay, sound, pitch, vol);
        this.title = title;
    }

    @Override
    public void display(Game game) {
        super.display(game);
        HUDManager.PublicHUD publicHUD = game.getManager(HUDManager.class).getPublicHUD();
        if (publicHUD.containsTitle("gapi_countdowntitle"))
            publicHUD.updateTitle("gapi_countdowntitle", title);
        else
            publicHUD.addTitle("gapi_countdowntitle", new HUDText(title, HUDPriority.HIGH));
    }

    @Override
    public void finish(Game game) {
        HUDManager.PublicHUD publicHUD = game.getManager(HUDManager.class).getPublicHUD();
        publicHUD.removeTitle("gapi_countdowntitle");
        super.finish(game);
    }
}
