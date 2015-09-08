package com.exorath.game.api.particles.effects;

import com.exorath.game.api.particles.ParticleEffect;
import com.exorath.game.api.particles.ParticleLocation;
import com.exorath.game.api.particles.ParticleType;

/**
 * Created by TOON on 8/30/2015.
 */
public class CubeEffect extends ParticleEffect {

    private float size;
    private int particles;

    public CubeEffect(ParticleLocation baseLocation, ParticleType type, float size, int particles, boolean repeating) {
        super(baseLocation, type, 1, 0, repeating);
        setup(size, particles);
    }

    public CubeEffect(ParticleLocation baseLocation, ParticleType type, int delay, float size, int particles,
            boolean repeating) {
        super(baseLocation, type, 1, delay, repeating);
        setup(size, particles);
    }

    private void setup(float size, int particles) {
        this.size = size;
        this.particles = particles;
    }

    @Override
    public void sendParticles(float frame) {
        float offset = size / 2;
        for (int i = 0; i < 1; i += 1 / particles) {
            sendParticle(offset - i, offset, offset);
            sendParticle(offset - i, -offset, offset);
            sendParticle(offset - i, offset, -offset);
            sendParticle(offset - i, -offset, -offset);

            sendParticle(offset, offset - i, offset);
            sendParticle(-offset, offset - i, offset);
            sendParticle(offset, offset - i, -offset);
            sendParticle(-offset, offset - i, -offset);

            sendParticle(offset, offset, offset - i);
            sendParticle(-offset, offset, offset - i);
            sendParticle(offset, -offset, offset - i);
            sendParticle(-offset, -offset, offset - i);
        }
    }
}
