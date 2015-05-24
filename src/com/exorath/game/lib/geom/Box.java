package com.exorath.game.lib.geom;

import org.bukkit.Location;

/**
 * Created by too on 24/05/2015.
 */
public class Box implements Region{
    private Location minLoc;
    private double width;
    private double height;
    private double length;
    public Box(Location minLoc, double size){
        minLoc = minLoc;
        setWHL(size,size,size);
    }
    public Box(Location minLoc, double width, double height, double length){
        minLoc = minLoc;
        setWHL(width, height, length);
    }
    public void setWHL(double width, double height, double length){
        this.width = width;
        this.height = height;
        this.length = length;
    }
    @Override
    public boolean isInRegion(Location loc) {
        double dX = loc.getX() - minLoc.getX();
        if(dX < 0 ) return false;
        if(dX > width) return false;
        double dY = loc.getY() - minLoc.getY();
        if(dY < 0 ) return false;
        if(dY > height) return false;
        double dZ = loc.getZ() - minLoc.getZ();
        if(dZ < 0 ) return false;
        if(dZ > length) return false;

        return true;
    }
}
