package com.exorath.game.api.particles.effect;

import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.exorath.game.api.Getter;
import com.exorath.game.api.particles.Particle;
import com.exorath.game.api.particles.ParticleBuilder;
import com.google.common.collect.Sets;

/**
 * @author Nick Robson
 */
public class SphereEffect {

    public static class Hollow implements ParticleEffect {

        private final Set<Particle> particles = Sets.newHashSet();

        private Effect type;
        private Getter<Location> middle;
        private double radius, step;

        public Hollow(Effect type, Getter<Location> middle, double radius, double step) {
            this.type = type;
            this.middle = middle;
            this.radius = radius;
            this.step = step;
        }

        @Override
        public Set<Particle> getParticles() {
            return particles;
        }

        @Override
        public void display(Player... players) {
            Location middle = this.middle.get();
            double rs = radius * radius;
            for (double x = -1; x <= 1; x += step)
                for (double y = -1; y <= 1; y += step)
                    for (double z = -1; z <= 1; z += step) {
                        Location loc = middle.clone().add(radius * x, radius * y, radius * z);
                        if (Math.abs(loc.distanceSquared(middle) - rs) <= step * 5)
                            ParticleBuilder.newBuilder().type(type)
                                    .location(loc).build().display(players);
                    }
        }

    }

    public static class Solid implements ParticleEffect {

        private final Set<Particle> particles = Sets.newHashSet();

        private Effect type;
        private Getter<Location> middle;
        private double radius, step;

        public Solid(Effect type, Getter<Location> middle, double radius, double step) {
            this.type = type;
            this.middle = middle;
            this.radius = radius;
            this.step = step;
        }

        @Override
        public Set<Particle> getParticles() {
            return particles;
        }

        @Override
        public void display(Player... players) {
            Location middle = this.middle.get();
            double rs = radius * radius;
            for (double x = -1; x <= 1; x += step)
                for (double y = -1; y <= 1; y += step)
                    for (double z = -1; z <= 1; z += step) {
                        Location loc = middle.clone().add(radius * x, radius * y, radius * z);
                        if (loc.distanceSquared(middle) - rs <= 0)
                            ParticleBuilder.newBuilder().type(type)
                                    .location(loc).build().display(players);
                    }
        }

    }

}
