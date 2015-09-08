package com.exorath.game.lib.geom;

import org.bukkit.Location;

/**
 * Created by too on 24/05/2015.
 */
public class CuboidRegion implements Region {

    private Location minLoc;
    private double width;
    private double height;
    private double length;

    public CuboidRegion(Location minLoc, double size) {
        this.minLoc = minLoc;
        this.setWHL(size, size, size);
    }

    public CuboidRegion(Location minLoc, double width, double height, double length) {
        this.minLoc = minLoc;
        this.setWHL(width, height, length);
    }

    public void setWHL(double width, double height, double length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    @Override
    public boolean isInRegion(Location loc) {
        double dX = loc.getX() - this.minLoc.getX();
        if (dX < 0) {
            return false;
        }
        if (dX > this.width) {
            return false;
        }
        double dY = loc.getY() - this.minLoc.getY();
        if (dY < 0) {
            return false;
        }
        if (dY > this.height) {
            return false;
        }
        double dZ = loc.getZ() - this.minLoc.getZ();
        if (dZ < 0) {
            return false;
        }
        if (dZ > this.length) {
            return false;
        }
        return true;
    }
}
