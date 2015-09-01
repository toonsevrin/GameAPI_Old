package com.exorath.game.api.particles.location;

import com.exorath.game.api.particles.ParticleLocation;
import com.exorath.game.api.player.GamePlayer;
import org.bukkit.Location;

/**
 * Created by TOON on 8/30/2015.
 */
public class PlayerLocation implements ParticleLocation {
    private GamePlayer player;
    public PlayerLocation(GamePlayer player){
        this.player = player;
    }
    @Override
    public Location getBaseLocation() {
        if(!player.isOnline())
            return null;
        return player.getBukkitPlayer().getLocation();
    }
}
