package com.exorath.game.api.gametype.minigame.countdown;

import com.exorath.game.api.Game;
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
        this.pitch = pitch;
        this.vol = vol;
    }

    @Override
    public void display(Game game) {
        super.display(game);
        game.getPlayers().getPlayers().forEach(gp -> gp.getBukkitPlayer().playSound(gp.getBukkitPlayer().getLocation(), sound, pitch, vol));
    }
}
