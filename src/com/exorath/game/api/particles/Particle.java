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

    default void display(Player player) {
        Location loc = getLocation();
        if (player.getLocation().getWorld().getName().equals(loc.getWorld().getName()))
            if (player.getLocation().distanceSquared(loc) <= getMeta().radius * getMeta().radius)
                player.spigot().playEffect(loc, getType(), getMeta().id, getMeta().data,
                        getMeta().offsetX, getMeta().offsetY, getMeta().offsetZ,
                        getMeta().speed, getMeta().amount, getMeta().radius);
    }

    default void display(Player... players) {
        Location loc = getLocation();
        int ds = getMeta().radius * getMeta().radius;
        if (players.length == 0) {
            for (Player player : loc.getWorld().getPlayers())
                if (player.getLocation().distanceSquared(loc) <= ds)
                    display(player);
        } else
            for (Player player : players)
                if (player.getLocation().distanceSquared(loc) <= ds)
                    display(player);
    }

}
