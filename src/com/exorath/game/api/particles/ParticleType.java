package com.exorath.game.api.particles;

import org.bukkit.Location;

/**
 * Created by TOON on 8/28/2015.
 */
public abstract class ParticleType {

    private ParticleEffect effect;

    protected void setEffect(ParticleEffect effect) {
        this.effect = effect;
    }

    public ParticleEffect getEffect() {
        return effect;
    }

    protected abstract void display(Location loc);
}
