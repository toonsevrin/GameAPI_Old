package com.exorath.game.api.particles;

/**
 * @author Nick Robson
 */
public class ParticleMeta {

    int id, data, amount, radius;
    float speed, offsetX, offsetY, offsetZ = 0;

    ParticleMeta() {
        speed = 0.001f;
        amount = 1;
        radius = 64;
        id = data = 0;
        offsetX = offsetY = offsetZ = 0;
    }

    @Override
    public ParticleMeta clone() {
        ParticleMeta clone = new ParticleMeta();
        clone.amount = amount;
        clone.speed = speed;
        clone.radius = radius;
        clone.offsetX = offsetX;
        clone.offsetY = offsetY;
        clone.offsetZ = offsetZ;
        return clone;
    }

}
