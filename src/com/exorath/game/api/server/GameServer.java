package com.exorath.game.api.server;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.exorath.game.api.players.GPlayer;
import com.google.common.collect.Sets;

public class GameServer {
    
    public static Set<GPlayer> getOnlinePlayers() {
        Set<GPlayer> s = Sets.newHashSet();
        for ( Player p : Bukkit.getOnlinePlayers() ) {
            s.add( GPlayer.fromUUID( p.getUniqueId() ) );
        }
        return s;
    }
    
}
