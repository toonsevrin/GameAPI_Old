package com.exorath.game.api.maps;

import org.bukkit.Location;

/**
 * @author Nick Robson
 */
public class Spawn {

    private int id;
    private Location location;

    public Spawn(int id, Location location) {
        this.id = id;
        this.location = location;
    }

    public int getID() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

}
