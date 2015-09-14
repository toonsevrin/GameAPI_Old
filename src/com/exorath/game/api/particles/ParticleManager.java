package com.exorath.game.api.particles;

import java.util.Set;

import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.google.common.collect.Sets;

/**
 * Created by TOON on 8/28/2015.
 */
public class ParticleManager {

    private final Set<ParticleEffect> particles = Sets.newHashSet();

    public void sendParticle(ParticleEffect effect) {
        particles.add(effect);
    }

    public void cancelParticle(ParticleEffect effect) {
        if (!particles.remove(effect))
            GameAPI.printConsole("Failed to remove ParticleEffectType as it doesn't exist");
    }

    private class UpdateParticles extends BukkitRunnable {

        @Override
        public void run() {
            particles.removeIf(particle -> !particle.isEnabled());

            for (ParticleEffect effect : particles)
                effect.tick();
        }
    }
}
