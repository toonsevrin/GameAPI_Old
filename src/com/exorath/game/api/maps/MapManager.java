package com.exorath.game.api.maps;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.util.Schematic;

/**
 * Created by TOON on 6/27/2015.
 * Worlds are all into GameAPI/maps/... and are loaded into the root of the server as normal worlds
 * (.schematic files are translated to SCHEM_schematicName worlds (CreateWorldFromSchematic
 * Minigames: the Minigame object automatically cycles through maps loaded in here
 * Gamemodes: Usually there is a main world and sometimes there are some other worlds. The main
 * world can be manually selected but by default it's the first map loaded.
 */
public class MapManager {
    private static final String SCHEMATIC_PREFIX = "SCHEM_";

    private File mapsFolder;
    ConfigurationSection mapsSection;

    private Set<String> worldNames = new HashSet<String>();
    private World defaultWorld;

    public MapManager() {
        this.mapsFolder = new File( GameAPI.getInstance().getDataFolder(), "maps" );//Should be the folder maps under GameAPI
        this.mapsSection = GameAPI.getInstance().getVersionsConfig().getConfigurationSection( "" );

        this.loadMaps();//load all maps and update schematics if they have changed

        GameAPI.getInstance().saveVersionsConfig();//Save the config, as some maps "updated" status might have changed
    }

    /**
     * Check whether or not the schematics have been updated (Normal worlds will be updated by the
     * startupScript.
     * If these have schematics updated: load them into a new world
     */
    private void loadMaps() {
        for ( String key : this.mapsSection.getKeys( false ) ) {//key is <schematicName>.schematic
            String worldName;
            if ( key.contains( ".schematic" ) ) {
                worldName = MapManager.SCHEMATIC_PREFIX + key.split( ".schematic" )[ 0 ];
                this.worldNames.add( worldName );
                if ( this.mapsSection.getBoolean( key + ".updated" ) ) {//if schematic has updated, update it
                    try {
                        this.createWorldFromSchematic( key );
                        this.mapsSection.set( key + ".updated", false );
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }
                }
            } else {
                worldName = key;
                this.worldNames.add( worldName );
                continue;
            }
            if ( this.worldNames.size() == 1 ) {
                this.defaultWorld = this.getWorld( worldName );
            }

        }
    }

    /**
     * This method creates a new world for a schematic that has been updated
     * TODO: USE VOID GENERATOR
     *
     * @param schematic
     *            The schematic that will appear in the world
     * @throws IOException
     *             Exception while deleting the previous world if it's there
     */
    private void createWorldFromSchematic( String schematic ) throws IOException {
        String worldName = MapManager.SCHEMATIC_PREFIX + schematic.split( ".schematic" )[ 0 ];
        File file = new File( worldName );//Folder named
        if ( file.exists() && file.isDirectory() ) {
            FileUtils.deleteDirectory( file );
        }

        World world = GameAPI.getInstance().getServer().createWorld( new WorldCreator( worldName ) );
        this.loadSchematicInWorld( schematic, world );
    }

    private void loadSchematicInWorld( String schemName, World world ) {
        File schematicFile = new File( this.mapsFolder, schemName );//This is the schematic file loaded by the startup script
        if ( schematicFile.isFile() ) {//Double check if the file has been loaded in by the startup script
            try {
                Schematic schematic = Schematic.load( schematicFile );//Load the file into a schematic object
                schematic.paste( world, new Location( world, 0, 0, 0 ) );//Paste the schematic within the world
                return;
            } catch ( Exception ex ) {}
        }
        //This is bad, the schematic file wasn't loaded!
        GameAPI.error( "Schematic file not found (GameAPI/maps/" + schemName
                + ") while it does appear in versions.yml. Is the startupScript configured? Have the maps been prepared on the main dedi?" );
    }

    public Set<String> getWorldNames() {
        return this.worldNames;
    }

    /**
     * Returns the map by name, if theres no map with this name, return null
     *
     * @param name
     *            name of the map
     * @return The map by name, if theres no map with this name, return null
     */
    public World getWorld( String name ) {
        if ( this.worldNames.contains( name ) ) {
            return Bukkit.getWorld( name );
        }
        if ( this.worldNames.contains( MapManager.SCHEMATIC_PREFIX + name ) ) {
            return Bukkit.getWorld( MapManager.SCHEMATIC_PREFIX + name );
        }
        return null;
    }

    public Set<World> getWorlds() {
        Set<World> worlds = new HashSet<World>();
        for ( String name : this.worldNames ) {
            worlds.add( Bukkit.getWorld( name ) );
        }
        return worlds;
    }
}
