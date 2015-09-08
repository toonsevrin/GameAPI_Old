package com.exorath.game.lib.geom;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

/**
 * Created by too on 24/05/2015.
 */
public interface Region {

    public boolean isInRegion(Location loc);

    public default boolean isInRegion(Entity entity) {
        return this.isInRegion(entity.getLocation());
    }

    public default boolean isInRegion(Block block) {
        return this.isInRegion(block.getLocation());
    }

}
