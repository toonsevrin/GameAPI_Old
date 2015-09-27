package com.exorath.game.api.particles.effect;

import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.exorath.game.api.particles.Particle;
import com.exorath.game.api.particles.ParticleBuilder;
import com.google.common.collect.Sets;

/**
 * @author Nick Robson
 */
public class LineEffect implements ParticleEffect {

    private final Set<Particle> particles = Sets.newHashSet();

    private Effect type;
    private Location a, b;
    private double step;

    public LineEffect(Effect type, Location a, Location b, double step) {
        this.type = type;
        this.a = a;
        this.b = b;
        this.step = Math.max(0.05, step);
        Validate.isTrue(a.getWorld().getName().equals(b.getWorld().getName()), "Locations must be in the same world.");
    }

    @Override
    public Set<Particle> getParticles() {
        return particles;
    }

    @Override
    public void init() {
        double distance = a.distance(b);
        Vector direction = b.toVector().subtract(a.toVector()).normalize();
        for (double d = 0; d < distance; d += step)
            particles.add(ParticleBuilder.newBuilder().type(type)
                    .location(a.toVector().add(direction.clone().multiply(d)).toLocation(a.getWorld()))
                    .build());
    }

}
