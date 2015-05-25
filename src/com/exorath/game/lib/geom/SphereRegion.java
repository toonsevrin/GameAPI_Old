package com.exorath.game.lib.geom;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;

public class SphereRegion implements Region {
    
    private Location centre;
    private double radius, radiusSquared;
    
    public SphereRegion( Location centre, double radius ) {
        Validate.isTrue( centre != null && centre.getWorld() != null, "middle must not be null" );
        Validate.isTrue( radius > 0, "radius must be positive" );
        this.centre = centre;
        this.radius = radius;
        this.radiusSquared = Math.pow( radius, 2 );
    }
    
    public Location getCentre() {
        return this.centre;
    }
    
    public double getRadius() {
        return this.radius;
    }
    
    @Override
    public boolean isInRegion( Location loc ) {
        if ( loc.getWorld() != this.centre.getWorld() ) {
            return false;
        } else {
            return this.centre.distanceSquared( loc ) <= this.radiusSquared;
        }
    }
}
