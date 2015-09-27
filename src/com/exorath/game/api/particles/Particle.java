package com.exorath.game.api.particles;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Nick Robson
 */
public interface Particle {

    void init();

    void tick();

    void uninit();

    boolean isEnabled();

    ParticleMeta getMeta();

    Location getLocation();

    Effect getType();

    void setEnabled(boolean enabled);

    default void display() {
        Location loc = getLocation();
        int ds = getMeta().radius * getMeta().radius;
        for (Player player : loc.getWorld().getPlayers())
            if (player.getLocation().distanceSquared(loc) <= ds)
                player.spigot().playEffect(loc, getType(), getMeta().id, getMeta().data,
                        getMeta().offsetX, getMeta().offsetY, getMeta().offsetZ,
                        getMeta().speed, getMeta().amount, getMeta().radius);
    }

}
