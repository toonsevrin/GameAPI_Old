package com.exorath.game.api.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by TOON on 8/28/2015.
 */
public interface ParticleType {

    void display(Location loc, Player p);
}

