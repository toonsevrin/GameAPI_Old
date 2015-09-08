package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.api.player.GamePlayer;
import org.bukkit.Sound;

/**
 * Created by TOON on 9/2/2015.
 */
public class SoundCountdownFrame extends SubtitleFrame {

    private Sound sound;
    private float pitch;
    private float vol;

    public SoundCountdownFrame(MinigameCountdown minigameCountdown, String text, int delay, Sound sound, float pitch,
            float vol) {
        super(minigameCountdown, text, delay);
        this.sound = sound;
    }

    @Override
    public void display(GamePlayer player) {
        super.display(player);
        player.getBukkitPlayer().playSound(player.getBukkitPlayer().getLocation(), sound, pitch, vol);
    }
}
