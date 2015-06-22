package example.survivalgames;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.geometry.Point3D;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * Created by too on 6/06/2015.
 */
public class SGChests {
    
    private static final Random RANDOM = new Random();
    private Set<SGChest> chests = new HashSet<SGChest>(); //A list with all loaded chests
    
    public SGChests( SurvivalGames game ) {
        World world = game.getBaseGameWorld(); //Get the default/main Game World
        
        FileConfiguration chestConfig = game.getConfig( "chests" ); //Get the config file "chests.yml"
        if ( !chestConfig.contains( world.getName() ) )
        {
            return; //If there isn't a root with the worlds name, return.
        }
        
        ConfigurationSection section = chestConfig.getConfigurationSection( world.getName() ); //Returns the section under the worlds name
        for ( String key : section.getKeys( false ) ) {
            this.chests.add( new SGChest( section.getConfigurationSection( key ) ) );
        }
        
        for ( SGChest chest : this.chests ) { //Place loaded chests in world and fill them
            if ( !chest.isValid() )
            {
                continue; //If the chest wasn't loaded right, continue.
            }
            Block block = world.getBlockAt( (int) chest.getCoordinates().getX(), (int) chest.getCoordinates().getY(), (int) chest.getCoordinates()
                    .getZ() );
            block.setType( Material.CHEST );
            Chest chestBlock = (Chest) block.getState();
            if ( chest.isDoubleChest() ) {
                world.getBlockAt( (int) chest.getSecondCoordinates().getX(), (int) chest.getSecondCoordinates().getY(),
                        (int) chest.getSecondCoordinates().getZ() ).setType( Material.CHEST );
            }
            
            chestBlock.getInventory().clear(); //Clear the chests inventory (To make sure previous items are gone)
            for ( int slot : chest.getItems().keySet() ) { //Go through all the loaded items and place them in the chests inventory
                chestBlock.getInventory().setItem( slot, chest.getItems().get( slot ) );
            }
        }
    }
    
    public void loadChests( SurvivalGames game ) {
        
    }
    
    //chest:
    // x: <x-coordinate>
    // Z: <y-coordinate>
    // Z: <z-coordinate>
    // x2: <x-coordinate (double chest, optional)>
    // y2: <y-coordinate (double chest, optional)>
    // z2: <z-coordinate (double chest, optional)>
    private class SGChest {
        
        private final int MIN_ITEMS = 1;
        private final int MAX_ITEMS = 10;
        
        private Point3D coordinates; //Coordinates of the first part, mandatory!
        private Point3D secondCoordinates; //Coordinates of the second part.
        private HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>(); //HashMap with slot and item
        
        public SGChest( ConfigurationSection section ) {
            if ( !this.isDouble( section, "x", "y", "z" ) ) {
                return;
            }
            this.coordinates = new Point3D( section.getDouble( "x" ), section.getDouble( "y" ), section.getDouble( "z" ) );
            
            if ( this.isDouble( section, "x2", "y2", "z2" ) ) {
                this.secondCoordinates = new Point3D( section.getDouble( "x" ), section.getDouble( "y" ), section.getDouble( "z" ) );
                if ( !this.isDoubleChestValid() ) {
                    this.secondCoordinates = null;
                }
            }
            this.setupItems();
        }
        
        private boolean isDouble( ConfigurationSection section, String... values ) {
            for ( String value : values ) {
                if ( !section.contains( value ) ) {
                    return false;
                }
            }
            for ( String value : values ) {
                if ( !section.isDouble( value ) ) {
                    return false;
                }
            }
            return true;
        }
        
        private boolean isDoubleChestValid() {
            if ( this.coordinates.getY() != this.secondCoordinates.getY() ) {
                return false;
            }
            if ( Math.abs( this.coordinates.getX() - this.secondCoordinates.getX() ) != 1 ) {
                return false;
            }
            if ( Math.abs( this.coordinates.getZ() - this.secondCoordinates.getZ() ) != 1 ) {
                return false;
            }
            return true;
        }
        
        private void setupItems() {
            this.generateItems( 0 ); //Fill the first chest
            if ( this.secondCoordinates != null ) { //If there is a second chest, fill that chest too
                this.generateItems( 27 );
            }
        }
        
        private void generateItems( int minSlot ) {
            int itemAmount = this.MIN_ITEMS + SGChests.RANDOM.nextInt( this.MAX_ITEMS - this.MIN_ITEMS + 1 ); //Generate random number with and between min and max
            for ( int i = 0; i < itemAmount; i++ ) {
                int slot = minSlot + SGChests.RANDOM.nextInt( 27 ); //generate random int between and with minSlot and minSlot + 26
                this.items.put( slot, this.getRandomItem() ); //Place a random item in the slot
            }
        }
        
        private ItemStack getRandomItem() {
            return null;
        }
        
        public boolean isValid() {
            return this.coordinates != null;
        }
        
        public boolean isDoubleChest() {
            return this.secondCoordinates != null;
        }
        
        public Point3D getCoordinates() {
            return this.coordinates;
        }
        
        public Point3D getSecondCoordinates() {
            return this.secondCoordinates;
        }
        
        public HashMap<Integer, ItemStack> getItems() {
            return this.items;
        }
    }
}
