package com.exorath.game.api.particles.effect;

import java.util.List;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.exorath.game.api.Getter;
import com.exorath.game.api.particles.Particle;
import com.exorath.game.api.particles.ParticleBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Nick Robson
 */
public class CubeEffect {

    public static class Corners implements ParticleEffect {

        private final Set<Particle> particles = Sets.newHashSet();

        private Effect type;
        private Getter<Location> middle;
        private double sideLength;

        public Corners(Effect type, Getter<Location> middle, double sideLength) {
            this.type = type;
            this.middle = middle;
            this.sideLength = sideLength;
        }

        @Override
        public Set<Particle> getParticles() {
            return particles;
        }

        @Override
        public void display(Player... players) {
            Location middle = this.middle.get();
            double dist = sideLength / 2;
            for (int x = -1; x <= 1; x++)
                for (int y = -1; y <= 1; y++)
                    for (int z = -1; z <= 1; z++)
                        if (x != 0 && y != 0 && z != 0)
                            ParticleBuilder.newBuilder().type(type)
                            .location(middle.clone().add(dist * x, dist * y, dist * z))
                            .build().display(players);
        }

    }

    public static class Frame implements ParticleEffect {

        private final Set<Particle> particles = Sets.newHashSet();

        private Effect type;
        private Getter<Location> middle;
        private double sideLength, step;

        public Frame(Effect type, Getter<Location> middle, double sideLength, double step) {
            this.type = type;
            this.middle = middle;
            this.sideLength = sideLength;
            this.step = step;
        }

        @Override
        public Set<Particle> getParticles() {
            return particles;
        }

        @Override
        public void display(Player... players) {
            Location middle = this.middle.get();
            double dist = sideLength / 2;
            for (double x = -1; x <= 1; x += step)
                for (double y = -1; y <= 1; y += step)
                    for (double z = -1; z <= 1; z += step) {
                        List<Boolean> bools = Lists.newArrayList();
                        if (Math.abs(x) >= 1)
                            bools.add(true);
                        if (Math.abs(y) >= 1)
                            bools.add(true);
                        if (Math.abs(z) >= 1)
                            bools.add(true);
                        if (bools.size() >= 2)
                            ParticleBuilder.newBuilder().type(type)
                            .location(middle.clone().add(dist * x, dist * y, dist * z))
                                    .build().display(players);
                    }

        }

    }

    public static class Walls implements ParticleEffect {

        private final Set<Particle> particles = Sets.newHashSet();

        private Effect type;
        private Getter<Location> middle;
        private double sideLength, step;

        public Walls(Effect type, Getter<Location> middle, double sideLength, double step) {
            this.type = type;
            this.middle = middle;
            this.sideLength = sideLength;
            this.step = step;
        }

        @Override
        public Set<Particle> getParticles() {
            return particles;
        }

        @Override
        public void display(Player... players) {
            Location middle = this.middle.get();
            double dist = sideLength / 2;
            for (double x = -1; x <= 1; x += step)
                for (double y = -1; y <= 1; y += step)
                    for (double z = -1; z <= 1; z += step)
                        if (Math.abs(x) >= 1 || Math.abs(y) >= 1 || Math.abs(z) >= 1)
                            ParticleBuilder.newBuilder().type(type)
                            .location(middle.clone().add(dist * x, dist * y, dist * z))
                                    .build().display(players);

        }

    }

    public static class Solid implements ParticleEffect {

        private final Set<Particle> particles = Sets.newHashSet();

        private Effect type;
        private Getter<Location> middle;
        private double sideLength, step;

        public Solid(Effect type, Getter<Location> middle, double sideLength, double step) {
            this.type = type;
            this.middle = middle;
            this.sideLength = sideLength;
            this.step = step;
        }

        @Override
        public Set<Particle> getParticles() {
            return particles;
        }

        @Override
        public void display(Player... players) {
            Location middle = this.middle.get();
            double dist = sideLength / 2;
            for (double x = -1; x <= 1; x += step)
                for (double y = -1; y <= 1; y += step)
                    for (double z = -1; z <= 1; z += step)
                        ParticleBuilder.newBuilder().type(type)
                        .location(middle.clone().add(dist * x, dist * y, dist * z))
                                .build().display(players);

        }

    }

}
