package com.exorath.game.lib;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by too on 24/05/2015.
 */
public class GAPIItemStack extends ItemStack {
    public GAPIItemStack(String name){
        setDisplayName(name);
    }
    public GAPIItemStack(Material material){
        super(material);
    }
    public GAPIItemStack(String name, Material material){
        super(material);
        setDisplayName(name);
    }
    public GAPIItemStack(String name, Material material, List<String> lore){
        super(material);
        setDisplayName(name);
        setLore(lore);
    }
    public GAPIItemStack(Material material, List<String> lore){
        super(material);
        setLore(lore);
    }
    public GAPIItemStack(List<String> lore){
        setLore(lore);
    }
    public void setDisplayName(String name){
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(name);
        setItemMeta(itemMeta);
    }
    public void setLore(List<String> lore){
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
    }
}
