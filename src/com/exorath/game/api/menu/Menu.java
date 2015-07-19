package com.exorath.game.api.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author Nick Robson
 */
public abstract class Menu {
    
    private int size;
    private Map<Integer, ItemStack> items = new HashMap<>();
    
    public Menu( int size ) {
        this.size = size;
        Validate.isTrue( size > 0, "Size must be greater than 0" );
        Validate.isTrue( size % 9 == 0, "Size must be divisible by 9" );
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Menu setItem( int index, ItemStack item ) {
        if ( index >= 0 && index < this.size ) {
            this.items.put( index, item == null
                    ? new ItemStack( Material.AIR ) : item );
        }
        return this;
    }
    
    public ItemStack getItem( int index ) {
        return this.items.getOrDefault( this.items, new ItemStack( Material.AIR ) );
    }
    
    public void dump( Inventory inventory ) {
        Validate.isTrue( inventory.getSize() >= this.getSize() );
        for ( Entry<Integer, ItemStack> entry : this.items.entrySet() ) {
            inventory.setItem( entry.getKey(), entry.getValue() );
        }
    }
    
}
