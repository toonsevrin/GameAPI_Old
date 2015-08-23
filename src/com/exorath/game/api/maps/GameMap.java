package com.exorath.game.api.maps;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.api.team.Team;
import com.yoshigenius.lib.serializable.Serializable;
import com.yoshigenius.lib.serializable.Serializer;

public class GameMap {

    // BASIC

    private static final java.util.Map<String, GameMap> worlds = new HashMap<>();

    public static GameMap get( String map ) {
        for ( GameMap m : GameMap.worlds.values() ) {
            if ( m.getName().equals( map ) ) {
                return m;
            }
        }
        return null;
    }

    public static GameMap get( World world ) {
        return GameMap.get( world, true );
    }

    public static GameMap get( World world, boolean load ) {
        for ( GameMap map : GameMap.worlds.values() ) {
            if ( map.getFolder().equals( world.getWorldFolder() ) ) {
                return map;
            }
        }
        if ( load ) {
            GameMap loaded = GameMap.loadFrom( world );
            if ( loaded != null ) {
                return loaded;
            }
        }
        return null;
    }

    public static GameMap loadFrom( World world ) {

        GameMap seed;
        if ( ( seed = GameMap.get( world, false ) ) != null ) {
            return seed;
        }

        File gamedata = new File( world.getWorldFolder(), "gamedata.yml" );
        if ( !gamedata.exists() ) {
            return null;
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration( gamedata );

        GameMap map = new GameMap( cfg.getString( "name" ), world );

        List<String> spawns = cfg.getStringList( "spawns" );
        List<String> spectators = cfg.getStringList( "spectators" );

        for ( String spawn : spawns ) {
            Serializable s = Serializer.deserialize( spawn );
            if ( s != null && s instanceof GameSpawn ) {
                map.spawns.add( (GameSpawn) s );
            }
        }

        for ( String spawn : spectators ) {
            Serializable s = Serializer.deserialize( spawn );
            if ( s != null && s instanceof GameSpawn ) {
                map.spectatorSpawns.add( (GameSpawn) s );
            }
        }

        GameMap.worlds.put( world.getName(), map );

        return map;
    }

    private String name;
    private File folder;
    private FileConfiguration config;

    public GameMap( String name, World world ) {
        this( name, world.getWorldFolder() );
    }

    public GameMap( String name, File folder ) {
        this.name = name;
        this.folder = folder;
    }

    public String getName() {
        return this.name;
    }

    public File getFolder() {
        return this.folder;
    }

    // ADVANCED

    private List<GameSpawn> spawns = new LinkedList<>();
    private List<GameSpawn> spectatorSpawns = new LinkedList<>();

    // spawns

    public GameSpawn[] getSpawns() {
        return this.spawns.toArray( new GameSpawn[this.spawns.size()] );
    }

    public GameSpawn getSpawn( int x ) {
        return this.getSpawn( null, x );
    }

    public GameSpawn getSpawn( Team team, int x ) {
        return GameSpawns.getSpawns( team ).getSpawn( x );
    }

    public void setSpawn( int spawn, Location loc ) {
        this.setSpawn( null, spawn, loc );
    }

    public void setSpawn( Team team, int spawn, Location loc ) {
        GameSpawns.getSpawns( team ).setSpawn( spawn, loc );
    }

    public void addSpawn( Location loc ) {
        this.addSpawn( null, loc );
    }

    public void addSpawn( Team team, Location loc ) {
        GameSpawns.getSpawns( team ).addSpawn( loc );
    }

    // spectator spawns

    public GameSpawn[] getSpectatorSpawns() {
        return this.spectatorSpawns.toArray( new GameSpawn[this.spectatorSpawns.size()] );
    }

    public void setSpectatorSpawn( int spawn, Location loc ) {
        if ( spawn < 0 ) {
            return;
        }
        GameSpawn location = new GameSpawn( loc );
        if ( this.spectatorSpawns.size() > spawn ) {
            this.spectatorSpawns.remove( spawn );
            this.spectatorSpawns.add( spawn, location );
        } else {
            this.spectatorSpawns.add( location );
        }
    }

    public void save() {
        FileConfiguration cfg = this.getConfig();

        List<String> sspawns = new LinkedList<>();
        List<String> ssspawns = new LinkedList<>();
        for ( GameSpawn spawn : this.spawns ) {
            sspawns.add( Serializer.serialize( spawn ) );
        }
        for ( GameSpawn spawn : this.spectatorSpawns ) {
            ssspawns.add( Serializer.serialize( spawn ) );
        }

        cfg.set( "name", this.getName() );
        cfg.set( "spawns", sspawns );
        cfg.set( "spectators", ssspawns );

        this.saveConfig();
    }

    public World getWorld() {
        for ( String worldName : GameMap.worlds.keySet() ) {
            if ( GameMap.worlds.get( worldName ) == this ) {
                return Bukkit.getWorld( worldName );
            }
        }
        return null;
    }

    public FileConfiguration getConfig() {
        if ( this.config != null ) {
            return this.config;
        }
        File gamedata = new File( this.folder, "gamedata.yml" );
        if ( !gamedata.exists() ) {
            return null;
        }
        return this.config = YamlConfiguration.loadConfiguration( gamedata );
    }

    public void saveConfig() {
        try {
            this.getConfig().save( new File( this.folder, "gamedata.yml" ) );
        } catch ( Exception ignored ) {}
    }

}
