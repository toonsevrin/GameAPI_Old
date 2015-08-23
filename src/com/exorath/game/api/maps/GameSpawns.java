package com.exorath.game.api.maps;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

import com.exorath.game.api.team.Team;

public class GameSpawns {

    public static final GameSpawns GLOBAL = new GameSpawns( null );
    private static final Map<Team, GameSpawns> all = new HashMap<>();

    public static GameSpawns getSpawns( Team team ) {
        if ( team == null ) {
            return GameSpawns.GLOBAL;
        }
        if ( !GameSpawns.all.containsKey( team ) ) {
            GameSpawns.all.put( team, new GameSpawns( team ) );
        }
        return GameSpawns.all.get( team );
    }

    private final List<GameSpawn> spawns = new LinkedList<>();

    private Team team;

    private GameSpawns( Team team ) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public GameSpawn[] getSpawns() {
        return this.spawns.toArray( new GameSpawn[this.spawns.size()] );
    }

    public GameSpawn getSpawn( int x ) {
        if ( x < 0 ) {
            return null;
        }
        while ( x >= this.spawns.size() ) {// e.g. if x = 10, spawns = 4, it will say to use 6, which
            // will then say to use 2, which is valid.
            x -= this.spawns.size();// e.g. if x = 4, spawns = 4, it will say to use 0, which is valid.
        }
        return this.getSpawns()[ x ];
    }

    public void setSpawn( int spawn, Location loc ) {
        if ( spawn < 0 ) {
            return;
        }
        GameSpawn location = new GameSpawn( loc );
        if ( spawn > this.spawns.size() ) {
            spawn = this.spawns.size();
        }
        if ( this.spawns.size() > spawn ) {
            this.spawns.remove( spawn );
            this.spawns.add( spawn, location );
        } else {
            this.spawns.add( location );
        }
    }

    public void addSpawn( Location loc ) {
        this.setSpawn( this.spawns.size(), loc );
    }

}
