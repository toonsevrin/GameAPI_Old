package com.exorath.game.api.menu;

import java.util.Map;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import com.exorath.game.api.Game;
import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Maps;

/**
 * @author Nick Robson
 */
public abstract class EasyMenu extends Menu {

    @FunctionalInterface
    public static interface EasyMenuItem {

        void onClick( InventoryClickEvent event, Game game, GamePlayer player );

    }

    private final Map<Integer, EasyMenuItem> metas = Maps.newHashMap();

    public EasyMenu( int size ) {
        super( size );
    }

    public EasyMenu setItem( int index, ItemStack item, EasyMenuItem meta ) {
        super.setItem( index, item );
        this.metas.put( index, meta );
        return this;
    }

    @Override
    public void onClick( InventoryClickEvent event, Game game, GamePlayer player ) {
        for ( int i = 0; i < event.getInventory().getSize(); i++ ) {
            if ( event.getCurrentItem().equals( this.getItem( i ) ) && this.metas.containsKey( i ) ) {
                event.setCancelled( true );
                this.metas.get( i ).onClick( event, game, player );
            }
        }
    }

    @Override
    public void onOpen( InventoryOpenEvent event, Game game, GamePlayer player ) {}

    @Override
    public void onClose( InventoryCloseEvent event, Game game, GamePlayer player ) {}

}
