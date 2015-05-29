package example.survivalgames;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.exorath.game.api.kit.Kit;
import com.yoshigenius.lib.util.ItemStackBuilder;

/**
 * @author Nick Robson
 */
public class SGKits {
    
    public static class WarriorKit extends Kit {
        
        private static final ItemStack icon = new ItemStackBuilder( Material.WOOD_SWORD ).getMeta().addLore( ChatColor.GOLD + "> Wood Sword" )
                .getBuilder().get();
        
        public WarriorKit() {
            super( "Warrior", WarriorKit.icon );
        }
        
        @Override
        public Map<Integer, ItemStack> getItems() {
            Map<Integer, ItemStack> items = new HashMap<>();
            items.put( 0, new ItemStackBuilder( Material.WOOD_SWORD ).get() );
            return items;
        }
        
        @Override
        public Map<Integer, ItemStack> getArmour() {
            Map<Integer, ItemStack> armour = new HashMap<>();
            return armour;
        }
        
        @Override
        public Map<PotionEffectType, Integer> getPotionEffects() {
            Map<PotionEffectType, Integer> effects = new HashMap<>();
            return effects;
        }
    }
    
    public static class ArcherKit extends Kit {
        
        private static final ItemStack icon = new ItemStackBuilder( Material.WOOD_SWORD ).getMeta().addLore( ChatColor.GOLD + "> Wood Sword" )
                .getBuilder().get();
        
        public ArcherKit() {
            super( "Archer", ArcherKit.icon );
        }
        
        @Override
        public Map<Integer, ItemStack> getItems() {
            Map<Integer, ItemStack> items = new HashMap<>();
            items.put( 0, new ItemStackBuilder( Material.BOW ).get() );
            items.put( 27, new ItemStackBuilder( Material.ARROW, 6 ).get() );
            return items;
        }
        
        @Override
        public Map<Integer, ItemStack> getArmour() {
            Map<Integer, ItemStack> armour = new HashMap<>();
            return armour;
        }
        
        @Override
        public Map<PotionEffectType, Integer> getPotionEffects() {
            Map<PotionEffectType, Integer> effects = new HashMap<>();
            return effects;
        }
        
    }
    
}
