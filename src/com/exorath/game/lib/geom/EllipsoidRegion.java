package com.exorath.game.lib.geom;

import org.bukkit.Location;

/**
 * Created by too on 24/05/2015.
 */
public class EllipsoidRegion implements Region {
    private Location center;
    private double rangeX;
    private double rangeY;
    private double rangeZ;
    
    public EllipsoidRegion( Location center, double range ) {
        this.center = center;
        setRange( range, range, range );
    }
    
    public EllipsoidRegion( Location center, double rangeX, double rangeY, double rangeZ ) {
        this.center = center;
        setRange( rangeX, rangeY, rangeZ );
    }
    
    public void setRange( double rangeX, double rangeY, double rangeZ ) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
        
    }
    
    @Override
    public boolean isInRegion( Location loc ) {
        if ( loc.getWorld() != center.getWorld() )
            return false;
        if ( Math.pow( ( loc.getX() / rangeX ), 2 ) + Math.pow( ( loc.getY() / rangeY ), 2 ) + Math.pow( ( loc.getZ() / rangeZ ), 2 ) <= 1 )
            return true;
        return false;
    }
}
