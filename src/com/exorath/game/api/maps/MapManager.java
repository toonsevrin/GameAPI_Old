package com.exorath.game.api.maps;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.util.Schematic;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by TOON on 6/27/2015.
 * Worlds are all into GameAPI/maps/... and are loaded into the root of the server as normal worlds (.schematic files are translated to SCHEM_schematicName worlds (CreateWorldFromSchematic
 * Minigames: the Minigame object automatically cycles through maps loaded in here
 * Gamemodes: Usually there is a main world and sometimes there are some other worlds. The main world can be manually selected but by default it's the first map loaded.
 *
 */
public class MapManager {
    private static final String SCHEMATIC_PREFIX = "SCHEM_";

    private File mapsFolder;
    ConfigurationSection mapsSection;

    private Set<String> worldNames = new HashSet<String>();
    private World defaultWorld;

    public MapManager() {
        mapsFolder = new File(GameAPI.getInstance().getDataFolder(), "maps"); //Should be the folder maps under GameAPI
        mapsSection = GameAPI.getInstance().getVersionsConfig().getConfigurationSection("");

        loadMaps(); //load all maps and update schematics if they have changed

        GameAPI.getInstance().saveVersionsConfig(); //Save the config, as some maps "updated" status might have changed
    }

    /**
     * Check whether or not the schematics have been updated (Normal worlds will be updated by the startupScript.
     * If these have schematics updated: load them into a new world
     */
    private void loadMaps() {
        for (String key : mapsSection.getKeys(false)) {//key is <schematicName>.schematic
            String worldName;
            if (key.contains(".schematic")) {
                worldName = SCHEMATIC_PREFIX + key.split(".schematic")[0];
                worldNames.add(worldName);
                if (mapsSection.getBoolean(key + ".updated")) {//if schematic has updated, update it
                    try {
                        createWorldFromSchematic(key);
                        mapsSection.set(key + ".updated", false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                worldName = key;
                worldNames.add(worldName);
                continue;
            }
            if(worldNames.size() == 1)//Set the default world if this is the first world loaded in
                defaultWorld = getWorld(worldName);

        }
    }
    /**
     * This method creates a new world for a schematic that has been updated
     * TODO: USE VOID GENERATOR
     *
     * @param schematic The schematic that will appear in the world
     * @throws IOException Exception while deleting the previous world if it's there
     */
    private void createWorldFromSchematic(String schematic) throws IOException {
        String worldName = SCHEMATIC_PREFIX + schematic.split(".schematic")[0];
        File file = new File(worldName); //Folder named
        if (file.exists() && file.isDirectory()) {
            FileUtils.deleteDirectory(file);
        }

        World world = GameAPI.getInstance().getServer().createWorld(new WorldCreator(worldName));
        loadSchematicInWorld(schematic, world);
    }

    private void loadSchematicInWorld(String schemName, World world) throws IOException {
        File schematicFile = new File(mapsFolder, schemName); //This is the schematic file loaded by the startup script
        if (schematicFile.isFile() && !schematicFile.isDirectory()) { //Double check if the file has been loaded in by the startup script
            Schematic schematic = Schematic.load(schematicFile); //Load the file into a schematic object
            schematic.paste(world, new Location(world, 0, 0, 0)); //Paste the schematic within the world
        } else { //This is bad, the schematic file wasn't loaded!
            GameAPI.error("Schematic file not found (GameAPI/maps/" + schemName + ") while it does appear in versions.yml. Is the startupScript configured? Have the maps been prepared on the main dedi?");
        }
    }
    public Set<String> getWorldNames(){
        return worldNames;
    }

    /**
     * Returns the map by name, if theres no map with this name, return null
     * @param name name of the map
     * @return The map by name, if theres no map with this name, return null
     */
    public World getWorld(String name){
        if(worldNames.contains(name))
            return Bukkit.getWorld(name);
        if(worldNames.contains(SCHEMATIC_PREFIX + name))
            return Bukkit.getWorld(SCHEMATIC_PREFIX + name);
        return null;
    }
    public Set<World> getWorlds(){
        Set<World> worlds = new HashSet<World>();
        for(String name : worldNames){
            worlds.add(Bukkit.getWorld(name));
        }
        return worlds;
    }
}
