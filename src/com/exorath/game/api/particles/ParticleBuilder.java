package com.exorath.game.api.particles;

import org.apache.commons.lang.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;

import com.exorath.game.api.Callback;
import com.exorath.game.api.Getter;

/**
 * @author Nick Robson
 */
public class ParticleBuilder {

    public static ParticleBuilder newBuilder() {
        return new ParticleBuilder();
    }

    private ParticleBuilder() {}

    private ParticleMeta meta = new ParticleMeta();
    private Effect type;
    private Callback<Particle> init, uninit;
    private Callback<Particle> ticker;
    private Getter<Location> location;

    public ParticleBuilder type(Effect type) {
        this.type = type;
        return this;
    }

    public ParticleMetaBuilder meta() {
        return new ParticleMetaBuilder(this, meta);
    }

    public ParticleBuilder init(Callback<Particle> init) {
        this.init = init;
        return this;
    }

    public ParticleBuilder uninit(Callback<Particle> uninit) {
        this.uninit = uninit;
        return this;
    }

    public ParticleBuilder location(Location location) {
        this.location = () -> location;
        return this;
    }

    public ParticleBuilder location(Getter<Location> location) {
        this.location = location;
        return this;
    }

    public ParticleBuilder ticker(Callback<Particle> ticker) {
        this.ticker = ticker;
        return this;
    }

    public Particle build() {

        Validate.notNull(type, "Particle is missing Type");
        Validate.notNull(location, "Particle is missing Location");

        final Effect type = this.type;
        final ParticleMeta meta = this.meta.clone();
        final Getter<Location> location = this.location;
        final Callback<Particle> init = this.init;
        final Callback<Particle> uninit = this.uninit;
        final Callback<Particle> ticker = this.ticker;

        return new Particle() {

            private boolean enabled = true;

            @Override
            public void tick() {
                if (ticker != null)
                    ticker.run(this);
                else
                    display();
            }

            @Override
            public ParticleMeta getMeta() {
                return meta;
            }

            @Override
            public boolean isEnabled() {
                return enabled;
            }

            @Override
            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            @Override
            public Effect getType() {
                return type;
            }

            @Override
            public Location getLocation() {
                return location.get();
            }

            @Override
            public void init() {
                if (init != null)
                    init.run(this);
            }

            @Override
            public void uninit() {
                if (uninit != null)
                    uninit.run(this);
            }

        };
    }

}
