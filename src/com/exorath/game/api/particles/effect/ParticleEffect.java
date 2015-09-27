package com.exorath.game.api.particles.effect;

import java.util.Set;

import com.exorath.game.api.particles.Particle;

/**
 * @author Nick Robson
 */
public interface ParticleEffect {

    Set<Particle> getParticles();

    default void init() {
        getParticles().forEach(p -> p.init());
    }

    default void display() {
        getParticles().forEach(p -> p.display());
    }

    default void uninit() {
        getParticles().forEach(p -> p.uninit());
    }

}
