package com.exorath.game.api.particles.effect;

import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.exorath.game.api.Getter;
import com.exorath.game.api.particles.Particle;
import com.exorath.game.api.particles.ParticleBuilder;
import com.google.common.collect.Sets;

/**
 * @author Nick Robson
 */
public class LineEffect implements ParticleEffect {

    private final Set<Particle> particles = Sets.newHashSet();

    private Effect type;
    private Getter<Location> a, b;
    private double step;

    public LineEffect(Effect type, Getter<Location> a, Getter<Location> b, double step) {
        this.type = type;
        this.a = a;
        this.b = b;
        this.step = Math.max(0.05, step);
    }

    @Override
    public Set<Particle> getParticles() {
        return particles;
    }

    @Override
    public void display() {
        Location a = this.a.get();
        Location b = this.b.get();
        double distance = a.distance(b);
        Vector direction = b.toVector().subtract(a.toVector()).normalize();
        for (double d = 0; d < distance; d += step)
            ParticleBuilder.newBuilder().type(type)
            .location(a.toVector().add(direction.clone().multiply(d))
                    .toLocation(a.getWorld()))
            .build().display();
    }

}
