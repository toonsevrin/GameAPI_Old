package com.exorath.game.api;

import org.bukkit.Sound;

/**
 * @author Nick Robson
 */
public class SoundInfo {

    private Sound sound;
    private float pitch, vol;

    public SoundInfo(Sound sound, float pitch, float vol) {
        this.sound = sound;
        this.pitch = pitch;
        this.vol = vol;
    }

    public Sound getSound() {
        return sound;
    }

    public float getPitch() {
        return pitch;
    }

    public float getVolume() {
        return vol;
    }

}
