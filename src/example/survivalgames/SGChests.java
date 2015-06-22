package example.survivalgames;

import javafx.geometry.Point3D;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by too on 6/06/2015.
 */
public class SGChests {
    private static final Random RANDOM = new Random();
    private Set<SGChest> chests = new HashSet<SGChest>(); //A list with all loaded chests

    public SGChests(SurvivalGames game){
        World world = game.getBaseGameWorld(); //Get the default/main Game World

        FileConfiguration chestConfig = game.getConfigYML("chests"); //Get the config file "chests.yml"
        if(!chestConfig.contains(world.getName())) return; //If there isn't a root with the worlds name, return.

        ConfigurationSection section = chestConfig.getConfigurationSection(world.getName()); //Returns the section under the worlds name
        for(String key : section.getKeys(false)) //Add all chests to the chest Collection
            chests.add(new SGChest(section.getConfigurationSection(key)));

        for(SGChest chest : chests){ //Place loaded chests in world and fill them
            if(!chest.isValid()) continue; //If the chest wasn't loaded right, continue.
            Block block = world.getBlockAt((int) chest.getCoordinates().getX(), (int) chest.getCoordinates().getY(), (int) chest.getCoordinates().getZ());
            block.setType(Material.CHEST);
            Chest chestBlock =  (Chest) block.getState();
            if(chest.isDoubleChest()) //If the chest is a double chest, place another chest on the second coordinate
                world.getBlockAt((int) chest.getSecondCoordinates().getX(), (int) chest.getSecondCoordinates().getY(), (int) chest.getSecondCoordinates().getZ()).setType(Material.CHEST);

            chestBlock.getInventory().clear(); //Clear the chests inventory (To make sure previous items are gone)
            for(int slot : chest.getItems().keySet()){ //Go through all the loaded items and place them in the chests inventory
                chestBlock.getInventory().setItem(slot, chest.getItems().get(slot));
            }
        }
    }
    public void loadChests(SurvivalGames game){

    }
    //chest:
    // x: <x-coordinate>
    // Z: <y-coordinate>
    // Z: <z-coordinate>
    // x2: <x-coordinate (double chest, optional)>
    // y2: <y-coordinate (double chest, optional)>
    // z2: <z-coordinate (double chest, optional)>
    private class SGChest{
        private final int MIN_ITEMS = 1;
        private final int MAX_ITEMS = 10;

        private Point3D coordinates; //Coordinates of the first part, mandatory!
        private Point3D secondCoordinates; //Coordinates of the second part.
        private HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>(); //HashMap with slot and item

        public SGChest(ConfigurationSection section){
            if(!isDouble(section, "x", "y", "z")) return;
            coordinates = new Point3D(section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));

            if(isDouble(section, "x2", "y2", "z2")){
                secondCoordinates = new Point3D(section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
                if(!isDoubleChestValid())
                    secondCoordinates = null;
            }
            setupItems();

        }
        private boolean isDouble(ConfigurationSection section, String... values){
            for(String value : values){
                if(!section.contains(value)) return false;
            }
            for(String value : values){
                if(!section.isDouble(value)) return false;
            }
            return true;
        }
        private boolean isDoubleChestValid(){
            if(coordinates.getY() != secondCoordinates.getY()) return false;
            if(Math.abs(coordinates.getX() - secondCoordinates.getX()) != 1) return false;
            if(Math.abs(coordinates.getZ() - secondCoordinates.getZ()) != 1) return false;
            return true;
        }
        private void setupItems(){
            generateItems(0); //Fill the first chest
            if(secondCoordinates != null){ //If there is a second chest, fill that chest too
                generateItems(27);
            }
        }
        private void generateItems(int minSlot){
            int itemAmount = MIN_ITEMS + RANDOM.nextInt(MAX_ITEMS - MIN_ITEMS + 1); //Generate random number with and between min and max
            for(int i = 0; i < itemAmount; i++){
                int slot = minSlot + RANDOM.nextInt(27); //generate random int between and with minSlot and minSlot + 26
                items.put(slot, getRandomItem()); //Place a random item in the slot
            }
        }
        private ItemStack getRandomItem(){
            return null;
        }
        public boolean isValid(){return coordinates != null;}
        public boolean isDoubleChest(){return secondCoordinates != null;}

        public Point3D getCoordinates(){return coordinates;}
        public Point3D getSecondCoordinates(){return secondCoordinates;}
        public HashMap<Integer, ItemStack> getItems(){return items;}
    }
}
