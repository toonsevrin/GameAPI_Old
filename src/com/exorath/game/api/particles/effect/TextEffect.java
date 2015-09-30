package com.exorath.game.api.particles.effect;

import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.exorath.game.api.Getter;
import com.exorath.game.api.particles.Particle;
import com.exorath.game.api.particles.ParticleBuilder;
import com.google.common.collect.Sets;

import me.nickrobson.lib.alphabet.Letter;

/**
 * @author Nick Robson
 */
public class TextEffect implements ParticleEffect {

    private final Set<Particle> particles = Sets.newHashSet();

    private Effect type;
    private Getter<Location> bottomLeft;
    private String text;
    private boolean xz;

    public TextEffect(Effect type, Getter<Location> bottomLeft, String text, boolean xz) {
        this.type = type;
        this.bottomLeft = bottomLeft;
        this.text = text;
        this.xz = xz;
    }

    @Override
    public Set<Particle> getParticles() {
        return particles;
    }

    @Override
    public void display(Player... players) {
        Location bottomLeft = this.bottomLeft.get();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            Letter letter = Letter.getLetter(c);
            if (letter != null) {
                double offset = Character.isUpperCase(c) ? 0.45 : 0.3;
                for (int x = 0; x < Letter.MAX_WIDTH; x++)
                    for (int y = 0; y < Letter.MAX_HEIGHT; y++)
                        if (letter.has(y, x))
                            ParticleBuilder.newBuilder().type(type)
                            .location(bottomLeft.clone().add(xz ? offset * x : 0, offset * (letter.getHeight() - y), xz ? 0 : offset * x))
                            .meta().speed(0).builder()
                            .build().display(players);
                bottomLeft = bottomLeft.clone().add(xz ? offset * (letter.getWidth() + 1) : 0, 0, xz ? 0 : offset * (letter.getWidth() + 1));
            }
        }
    }

}
