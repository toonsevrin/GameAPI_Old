package com.exorath.game.api.gametype.minigame.kit;

import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Maps;

/**
 * Created by too on 27/05/2015.
 */
public class ItemKit extends Kit {
    
    private Map<Integer, ItemStack> items = Maps.newHashMap();
    private ItemStack helmet;
    private ItemStack chestPlate;
    private ItemStack leggings;
    private ItemStack boots;
    
    public ItemKit( String name, ItemStack icon, String[] description ) {
        super( name, icon );
        this.setDescription( description );
    }
    
    //Item management
    public void addItem( ItemStack stack ) {
        this.items.put( this.items.size(), stack );
    }
    
    public void setHelmet( ItemStack helmet ) {
        this.helmet = helmet;
    }
    
    public void setChestPlate( ItemStack chestPlate ) {
        this.chestPlate = chestPlate;
    }
    
    public void setLeggings( ItemStack leggings ) {
        this.leggings = leggings;
    }
    
    public void setBoots( ItemStack boots ) {
        this.boots = boots;
    }
    
    public ItemStack[] getArmor() {
        return new ItemStack[] { this.helmet, this.chestPlate, this.leggings, this.boots };
    }
    
    @Override
    public Map<Integer, ItemStack> getItems() {
        return this.items;
    }
    
    @Override
    public Map<Integer, ItemStack> getArmour() {
        Map<Integer, ItemStack> armour = Maps.newHashMap();
        armour.put( 1, this.helmet );
        armour.put( 2, this.chestPlate );
        armour.put( 3, this.leggings );
        armour.put( 4, this.boots );
        return armour;
    }
    
    @Override
    public Map<PotionEffectType, Integer> getPotionEffects() {
        return Maps.newHashMap();
    }
    
}
