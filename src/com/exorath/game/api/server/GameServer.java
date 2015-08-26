package com.exorath.game.api.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class GameServer {

    private static GameServer server;

    static {
        server = new GameServer();
    }

    public static GameServer get() {
        return server;
    }

    private GameServer() {}

    private Map<String, GamePlayer> online = Maps.newHashMap();

    public void updateOnlinePlayers() {
        Iterator<Entry<String, GamePlayer>> it = online.entrySet().iterator();
        while ( it.hasNext() ) {
            Entry<String, GamePlayer> entry = it.next();
            if ( Bukkit.getPlayer( UUID.fromString( entry.getKey() ) ) == null ) {
                it.remove();
            }
        }
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            if ( !online.containsKey( player.getUniqueId().toString() ) ) {
                online.put( player.getUniqueId().toString(), new GamePlayer( player.getUniqueId() ) );
            }
        }
    }

    public Set<GamePlayer> getOnlinePlayers() {
        updateOnlinePlayers();
        return Sets.newHashSet( online.values() );
    }

}
