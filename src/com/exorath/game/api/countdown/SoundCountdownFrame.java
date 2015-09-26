package com.exorath.game.api.countdown;

import org.bukkit.Sound;

/**
 * @author Nick Robson
 */
public interface SoundCountdownFrame extends CountdownFrame {

    Sound getSound();

    float getPitch();

    float getVolume();

}
