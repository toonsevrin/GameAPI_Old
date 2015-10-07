package com.exorath.game.api.particles.effect;

import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.exorath.game.api.Getter;
import com.exorath.game.api.particles.Particle;
import com.exorath.game.api.particles.ParticleBuilder;
import com.google.common.collect.Sets;

/**
 * @author Nick Robson
 */
public class HelixEffect implements ParticleEffect {

    private final Set<Particle> particles = Sets.newHashSet();

    private Effect type;
    private Getter<Location> start;
    private double radius, height, stepAngle, stepY;
    private boolean clockwise, flipZ;

    public HelixEffect(Effect type, Getter<Location> start, double radius, double height, double stepAngle,
            double stepY, boolean clockwise, boolean flipZ) {
        this.type = type;
        this.start = start;
        this.radius = radius;
        this.height = height;
        this.stepAngle = stepAngle;
        this.stepY = stepY;
        this.clockwise = clockwise;
        this.flipZ = flipZ;
    }

    @Override
    public Set<Particle> getParticles() {
        return particles;
    }

    @Override
    public void display(Player... players) {
        Location start = this.start.get();
        int numParticles = (int) (height / stepY);
        double angle = 0, rs = radius * radius;
        final Vector zero = new Vector();
        for (int i = 0; i < numParticles; i++) {
            Vector vec = zero.clone();
            double dx = 0.1 * Math.sin(angle);
            double dz = 0.1 * Math.cos(angle);
            if (clockwise)
                dx = -dx;
            if (flipZ) {
                dx = -dx;
                dz = -dz;
            }
            while (vec.distanceSquared(zero) < rs)
                vec.add(new Vector(dx, 0, dz));
            vec.add(new Vector(0, stepY * i, 0));
            Location loc = vec.toLocation(start.getWorld()).add(start);
            angle = (angle + stepAngle) % 360;
            ParticleBuilder.newBuilder().type(type)
            .location(loc).build().display(players);
        }
    }

}
