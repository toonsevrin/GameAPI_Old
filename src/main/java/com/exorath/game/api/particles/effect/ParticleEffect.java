package com.exorath.game.api.particles.effect;

import java.util.Set;

import org.bukkit.entity.Player;

import com.exorath.game.api.particles.Particle;

/**
 * @author Nick Robson
 */
public interface ParticleEffect {

    Set<Particle> getParticles();

    default void init() {
        getParticles().forEach(p -> p.init());
    }

    default void display(Player... players) {
        getParticles().forEach(p -> p.display(players));
    }

    default void uninit() {
        getParticles().forEach(p -> p.uninit());
    }

}
