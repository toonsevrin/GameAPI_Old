package com.exorath.game.api.particles.effect;

import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.exorath.game.api.Getter;
import com.exorath.game.api.particles.Particle;
import com.google.common.collect.Sets;

/**
 * @author Nick Robson
 */
public class DoubleHelixEffect implements ParticleEffect {

    private final Set<Particle> particles = Sets.newHashSet();

    private HelixEffect a, b;

    public DoubleHelixEffect(Effect type, Getter<Location> start, double radius, double height, double offsetAngle, double stepAngle,
            double stepY, boolean clockwise) {
        a = new HelixEffect(type, start, radius, height, offsetAngle, stepAngle, stepY, clockwise);
        b = new HelixEffect(type, start, radius, height, offsetAngle + Math.PI, stepAngle, stepY, clockwise);
    }

    @Override
    public void display(Player... players) {
        a.display(players);
        b.display(players);
    }

    @Override
    public Set<Particle> getParticles() {
        return particles;
    }

}
