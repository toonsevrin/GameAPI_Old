package com.demo.kits;

import com.exorath.game.api.kits.ItemKit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by too on 30/05/2015.
 */
public class ArcherKit extends ItemKit{
    public ArcherKit(){
        super("Archer", Material.BOW, new String[]{"Spawn with some leather with a bow and 16 arrows."});

        addItem(new ItemStack(Material.BOW));
        addItem(new ItemStack(Material.ARROW));
        setHelmet(new ItemStack(Material.LEATHER_HELMET));
        setBoots(new ItemStack(Material.LEATHER_BOOTS));
    }
}
