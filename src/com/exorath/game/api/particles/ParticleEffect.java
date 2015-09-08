package com.exorath.game.api.particles;

import com.exorath.game.GameAPI;
import org.bukkit.Bukkit;

/**
 * Created by Toon Sevrin on 8/28/2015.
 */
public abstract class ParticleEffect implements Runnable {

    private ParticleLocation baseLocation;
    private boolean enabled;

    private Repeater repeater;
    private ParticleType type;

    private int frames;
    private int interval;

    private int tick = 0;
    private int frame = 0;

    //** Constructors **//
    public ParticleEffect(ParticleLocation baseLocation, ParticleType type, int frames, int interval,
            boolean repeating) {
        if (repeating)
            setup(baseLocation, type, frames, interval, new UnlimitedRepeater());
        else
            setup(baseLocation, type, frames, interval, new Repeater());
    }

    public ParticleEffect(ParticleLocation baseLocation, ParticleType type, int frames, int interval, int repeats) {
        setup(baseLocation, type, frames, interval, new LimitedRepeater(repeats));
    }

    private void setup(ParticleLocation baseLocation, ParticleType type, int frames, int interval, Repeater repeater) {
        this.baseLocation = baseLocation;
        this.frames = frames;
        this.interval = interval;

        this.repeater = repeater;
        this.type = type;
    }

    //** Abstract methods **//
    public abstract void sendParticles(float frame);

    //** Ran by childs **//
    public void sendParticle(float x, float y, float z) {
        type.display(baseLocation.getBaseLocation().clone().add(x, y, z));
    }

    //** Ran by manager **//
    public void tick() {
        if (!isReady())
            return;
        run();
    }

    @Override
    public void run() {
        if (!isBaseLocValid())
            return;
        frame++;

        sendParticles(((float) frame) / frames);

        if (!hasFramesRemaining())
            return;
        Bukkit.getScheduler().runTaskLater(GameAPI.getInstance(), this, interval);
    }

    //** Getters & Setters **//
    private boolean isReady() {
        tick++;
        if (tick == interval) {
            tick = 0;
            return true;
        }
        return false;
    }

    private boolean isBaseLocValid() {
        if (baseLocation.getBaseLocation() == null) {
            enabled = false;
            return false;
        }
        return true;
    }

    private boolean hasFramesRemaining() {
        if (frame >= frames) {
            if (!repeater.repeat()) {
                enabled = false;
            }
            return false;
        }
        return true;
    }

    public int getInterval() {
        return interval;
    }

    public int getFrames() {
        return frames;
    }

    public int getTotalTicks() {
        return interval * frames;
    }

    public int getFrame() {
        return frame;
    }

    public Repeater getRepeater() {
        return repeater;
    }

    public ParticleLocation getBaseLocation() {
        return baseLocation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    //** Repeating types **/
    public class Repeater {

        public boolean repeat() {
            return false;
        }
    }

    private class LimitedRepeater extends Repeater {

        private int amount;

        public LimitedRepeater(int amount) {
            this.amount = amount;
        }

        @Override
        public boolean repeat() {
            if (amount == 0)
                return false;
            amount--;
            return true;
        }
    }

    private class UnlimitedRepeater extends Repeater {

        @Override
        public boolean repeat() {
            return true;
        }
    }
}
