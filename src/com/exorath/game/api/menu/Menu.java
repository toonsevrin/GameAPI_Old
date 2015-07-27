package com.exorath.game.api.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;

/**
 * @author Nick Robson
 */
public abstract class Menu {
    
    private final int size;
    private Map<Integer, ItemStack> items = new HashMap<>();
    
    public Menu( int size ) {
        this.size = size;
        Validate.isTrue( size > 0, "Size must be greater than 0" );
        Validate.isTrue( size % 9 == 0, "Size must be divisible by 9" ); // I'm lazy. You're lazy. We're all lazy, right?
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Menu setItem( int index, ItemStack item ) {
        if ( index >= 0 && index < this.size ) {
            this.items.put( index, item == null ? new ItemStack( Material.AIR ) : item );
        }
        return this;
    }
    
    public ItemStack getItem( int index ) {
        return this.items.getOrDefault( this.items, new ItemStack( Material.AIR ) );
    }
    
    public void dump( Inventory inventory ) {
        Validate.isTrue( inventory.getSize() >= this.getSize(), "Inventory cannot fit this menu." );
        for ( Entry<Integer, ItemStack> entry : this.items.entrySet() ) {
            inventory.setItem( entry.getKey(), entry.getValue() );
        }
    }
    
    public boolean open( GamePlayer player ) {
        Validate.notNull( player, "Player cannot be null." );
        return player.openMenu( this );
    }
    
    public abstract void onOpen( InventoryOpenEvent event, Game game, GamePlayer player );
    
    public abstract void onClick( InventoryClickEvent event, Game game, GamePlayer player );
    
    public abstract void onClose( InventoryCloseEvent event, Game game, GamePlayer player );
    
}
