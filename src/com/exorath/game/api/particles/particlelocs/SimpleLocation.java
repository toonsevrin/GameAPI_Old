package com.exorath.game.api.particles.particleLocs;

import com.exorath.game.api.particles.ParticleLocation;
import org.bukkit.Location;

/**
 * Created by TOON on 8/30/2015.
 */
public class SimpleLocation implements ParticleLocation {
    private Location location;
    public SimpleLocation(Location location){
        this.location = location;
    }
    @Override
    public Location getBaseLocation() {
        return location;
    }
}
