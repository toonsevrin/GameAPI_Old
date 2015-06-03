package com.demo.kits;

import com.exorath.game.api.kits.ItemKit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by too on 30/05/2015.
 */
public class WarriorKit extends ItemKit{
    public WarriorKit(){
        super("Warrior", Material.IRON_SWORD, new String[]{"Fight in the arena in a leater", "chestplate and a sharp iron sword."});

        addItem(new ItemStack(Material.IRON_SWORD));
        setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
    }
}
