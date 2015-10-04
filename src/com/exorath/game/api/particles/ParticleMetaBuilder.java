package com.exorath.game.api.particles;

/**
 * @author Nick Robson
 */
public class ParticleMetaBuilder {

    private ParticleBuilder parent;
    private ParticleMeta meta;

    ParticleMetaBuilder(ParticleBuilder parent, ParticleMeta meta) {
        this.parent = parent;
        this.meta = meta;
    }

    public ParticleBuilder builder() {
        return parent;
    }

    public ParticleMetaBuilder id(int id) {
        meta.id = id;
        return this;
    }

    public ParticleMetaBuilder data(int data) {
        meta.data = data;
        return this;
    }

    public ParticleMetaBuilder amount(int amount) {
        meta.amount = amount;
        return this;
    }

    public ParticleMetaBuilder speed(float speed) {
        meta.speed = speed;
        return this;
    }

    public ParticleMetaBuilder radius(int radius) {
        meta.radius = radius;
        return this;
    }

    public ParticleMetaBuilder offset(float x, float y, float z) {
        meta.offsetX = x;
        meta.offsetY = y;
        meta.offsetZ = z;
        return this;
    }

    public ParticleMetaBuilder offsetX(float x) {
        meta.offsetX = x;
        return this;
    }

    public ParticleMetaBuilder offsetY(float y) {
        meta.offsetY = y;
        return this;
    }

    public ParticleMetaBuilder offsetZ(float z) {
        meta.offsetZ = z;
        return this;
    }

}
