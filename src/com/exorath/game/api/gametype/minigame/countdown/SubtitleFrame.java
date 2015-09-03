package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 9/2/2015.
 */
public class SubtitleFrame extends CountdownFrame{
    private String text;
    public SubtitleFrame(MinigameCountdown minigameCountdown, String text, int delay){
        super(minigameCountdown, delay);
    }

    @Override
    public void display(GamePlayer player) {
        if(getMinigameCountdown().getSubTitles().containsKey(player)) {
            getMinigameCountdown().getSubTitles().get(player).setText(text);
        }else{
            HUDText hudText = new HUDText(text, HUDPriority.HIGH);
            hudText.setText(text);
            getMinigameCountdown().getSubTitles().put(player, hudText);
        }
    }

    @Override
    public void finish() {
        for(GamePlayer player : getMinigameCountdown().getSubTitles().keySet())
            player.getHud().getSubtitle().removeText(getMinigameCountdown().getSubTitles().get(player));
    }
}
