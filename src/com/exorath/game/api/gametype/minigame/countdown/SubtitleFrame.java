package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.GameAPI;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.hud.locations.Subtitle;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 9/2/2015.
 */
public class SubtitleFrame extends CountdownFrame {
    private String text;

    public SubtitleFrame(MinigameCountdown minigameCountdown, String text, int delay) {
        super(minigameCountdown, delay);
    }

    @Override
    public void display(GamePlayer player) {
        Subtitle subtitle = player.getHud().getSubtitle();
        if (subtitle.containsText("countdown"))
            subtitle.getText("countdown").setText(text);
        else
            subtitle.addText("countdown", new HUDText(text, HUDPriority.HIGH));
    }

    @Override
    public void finish() {
        GameAPI.getOnlinePlayers().forEach(p -> p.getHud().getSubtitle().removeText("countdown"));
    }
}
