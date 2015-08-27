package com.exorath.game.api.maps;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.player.PlayerState;
import com.exorath.game.api.team.Team;
import com.exorath.game.api.team.TeamColor;
import com.exorath.game.api.team.TeamManager;
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

        map.spectatorSpawns = cfg.getStringList( "spectators" ).stream().map( Serializer::deserialize ).filter( s -> s instanceof GameSpawn ).map( s -> (GameSpawn) s ).collect( Collectors.toList() );

        ConfigurationSection ss = cfg.getConfigurationSection( "spawns" );

        for ( String key : ss.getKeys( false ) ) {
            if ( ss.isList( key ) ) {
                List<GameSpawn> list = ss.getStringList( "global" ).stream().map( Serializer::deserialize ).filter( s -> s instanceof GameSpawn ).map( s -> (GameSpawn) s ).collect( Collectors.toList() );
                if ( key.equals( "global" ) ) {
                    map.global = list;
                } else {
                    try {
                        Team team = Team.getTeam(TeamColor.valueOf( key.toUpperCase() ), false);
                        if ( team != null ) {
                            map.spawns.put( team, list );
                        }
                    } catch ( Exception ex ) {}
                }
            }
        }

        GameMap.worlds.put( world.getName(), map );

        return map;
    }

    public static void loadWorlds() {
        for ( World world : Bukkit.getWorlds() ) {
            loadFrom( world );
        }
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

    public void save() {
        FileConfiguration cfg = this.getConfig();
        cfg.set( "name", this.getName() );
        cfg.set( "spectators", this.spectatorSpawns.stream().map( Serializer::serialize ).collect( Collectors.toList() ) );
        cfg.set( "spawns.global", this.global.stream().map( Serializer::serialize ).collect( Collectors.toList() ) );
        for ( Entry<Team, List<GameSpawn>> entry : spawns.entrySet() ) {
            if ( entry.getKey() != null && entry.getValue() != null && !entry.getValue().isEmpty() ) {
                cfg.set( "spawns." + entry.getKey().getTeamColor().name().toLowerCase(), entry.getValue().stream().map( Serializer::serialize ).collect( Collectors.toList() ) );
            }
        }
        this.saveConfig();
    }

    // ADVANCED

    private List<GameSpawn> spectatorSpawns = new LinkedList<>();

    private List<GameSpawn> global = new LinkedList<>();
    private final Map<Team, List<GameSpawn>> spawns = new HashMap<>();

    // spawns

    private List<GameSpawn> getSpawnsList( Team team ) {
        if ( team == null ) {
            return global;
        }
        if ( !spawns.containsKey( team ) ) {
            spawns.put( team, new LinkedList<>() );
        }
        return spawns.get( team );
    }

    public GameSpawn[] getSpawns( Team team ) {
        List<GameSpawn> spawns = getSpawnsList( team );
        return spawns == null ? new GameSpawn[0] : spawns.toArray( new GameSpawn[spawns.size()] );
    }

    public GameSpawn getSpawn( int x ) {
        return this.getSpawn( null, x );
    }

    public GameSpawn getSpawn( Team team, int x ) {
        if ( x < 0 ) {
            return null;
        }
        List<GameSpawn> spawns = getSpawnsList( team );
        while ( x >= spawns.size() ) {// e.g. if x = 10, spawns = 4, it will say to use 6, which
            // will then say to use 2, which is valid.
            x -= spawns.size();// e.g. if x = 4, spawns = 4, it will say to use 0, which is valid.
        }
        return this.getSpawns( team )[ x ];
    }

    public void setSpawn( Team team, int spawn, Location loc ) {
        if ( spawn < 0 ) {
            return;
        }
        GameSpawn location = new GameSpawn( loc );
        List<GameSpawn> spawns = getSpawnsList( team );
        if ( spawn > spawns.size() ) {
            spawn = spawns.size();
        }
        if ( spawns.size() > spawn ) {
            spawns.remove( spawn );
            spawns.add( spawn, location );
        } else {
            spawns.add( location );
        }
    }

    public void addSpawn( Team team, GameSpawn spawn ) {
        getSpawnsList( team ).add( spawn );
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

    public GameSpawn findSpawn( GamePlayer player ) {
        Game game = player.getGame();
        if ( game != null ) {
            PlayerState state = player.getState( game );
            if ( state == PlayerState.PLAYING ) {
                TeamManager teams = game.getManager( TeamManager.class );
                Team team = teams == null ? null : teams.getTeam( player );
                List<GameSpawn> spawns = getSpawnsList( team );
                if ( spawns != null ) {
                    return spawns.get( new Random().nextInt( spawns.size() ) );
                }
            }
        }
        return null;
    }

    // World

    public World getWorld() {
        for ( String worldName : GameMap.worlds.keySet() ) {
            if ( GameMap.worlds.get( worldName ) == this ) {
                return Bukkit.getWorld( worldName );
            }
        }
        return null;
    }

    // Configuration

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

    public void reset() {
        // TODO: Things like undoing changes to the world.
    }

}
