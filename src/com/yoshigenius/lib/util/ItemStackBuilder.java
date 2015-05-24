package com.yoshigenius.lib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackBuilder {
    
    public static class ItemStackBuilderMeta {
        
        private final ItemStackBuilder builder;
        private final ItemMeta meta;
        
        protected ItemStackBuilderMeta( ItemStackBuilder builder, ItemMeta meta ) {
            this.builder = builder;
            this.meta = meta;
        }
        
        public ItemStackBuilder getBuilder() {
            this.builder.get().setItemMeta( this.meta );
            return this.builder;
        }
        
        public ItemStackBuilderMeta setDisplayName( String display ) {
            this.meta.setDisplayName( display );
            return this;
        }
        
        public ItemStackBuilderMeta clearLore() {
            return this.setLore( new ArrayList<String>() );
        }
        
        public ItemStackBuilderMeta setLore( List<String> lore ) {
            this.meta.setLore( lore );
            return this;
        }
        
        public ItemStackBuilderMeta setLore( String... lore ) {
            return this.setLore( Arrays.asList( lore ) );
        }
        
        public ItemStackBuilderMeta addLore( String lore ) {
            List<String> lores = this.meta.getLore();
            if ( lores == null ) {
                lores = new ArrayList<String>();
            }
            lores.add( lore );
            return this.setLore( lores );
        }
        
        public ItemStackBuilderMeta setColor( Color color ) {
            if ( this.meta instanceof LeatherArmorMeta ) {
                LeatherArmorMeta lameta = (LeatherArmorMeta) this.meta;
                lameta.setColor( color );
            }
            return this;
        }
        
        public ItemStackBuilderMeta setOwner( String owner ) {
            if ( this.meta instanceof SkullMeta ) {
                SkullMeta skullmeta = (SkullMeta) this.meta;
                skullmeta.setOwner( owner );
            } else if ( this.meta instanceof BookMeta ) {
                BookMeta bookmeta = (BookMeta) this.meta;
                bookmeta.setAuthor( owner );
            }
            return this;
        }
        
        public ItemStackBuilderMeta addStoredEnchantment( Enchantment ench, int level, boolean show ) {
            if ( this.meta instanceof EnchantmentStorageMeta ) {
                EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) this.meta;
                enchmeta.addStoredEnchant( ench, level, show );
            }
            return this;
        }
        
        public Map<Enchantment, Integer> getStoredEnchantments() {
            if ( this.meta instanceof EnchantmentStorageMeta ) {
                EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) this.meta;
                return enchmeta.getStoredEnchants();
            }
            return null;
        }
        
        public ItemStackBuilderMeta setTitle( String title ) {
            if ( this.meta instanceof BookMeta ) {
                BookMeta bookmeta = (BookMeta) this.meta;
                bookmeta.setTitle( title );
            }
            return this;
        }
        
        public ItemStackBuilderMeta addPage( String[] lines ) {
            if ( this.meta instanceof BookMeta ) {
                BookMeta bookmeta = (BookMeta) this.meta;
                bookmeta.addPage( lines );
            }
            return this;
        }
        
        public ItemStackBuilderMeta setPage( int index, String page ) {
            if ( this.meta instanceof BookMeta ) {
                BookMeta bookmeta = (BookMeta) this.meta;
                bookmeta.setPage( index, page );
            }
            return this;
        }
        
    }
    
    private final ItemStack itemStack;
    
    public ItemStackBuilder( Material m ) {
        this( m, 1 );
    }
    
    public ItemStackBuilder( Material m, byte data ) {
        this( m, 1, data );
    }
    
    public ItemStackBuilder( Material m, int num ) {
        this( m, num, (byte) 0 );
    }
    
    public ItemStackBuilder( Material m, int num, byte data ) {
        this( new ItemStack( m, num, data ) );
    }
    
    public ItemStackBuilder( Material m, int num, short data ) {
        this( new ItemStack( m, num, data ) );
    }
    
    public ItemStackBuilder( ItemStack base ) {
        this.itemStack = base;
        this.itemStack.getType().getMaxStackSize();
    }
    
    public ItemStackBuilder setMaterial( Material m ) {
        this.itemStack.setType( m );
        return this;
    }
    
    public ItemStackBuilder setAmount( int num ) {
        this.itemStack.setAmount( num );
        return this;
    }
    
    public ItemStackBuilder addEnchantment( Enchantment ench, int level ) {
        try {
            this.itemStack.addEnchantment( ench, level );
            return this;
        } catch ( Exception ex ) {
            return this.addUnsafeEnchantment( ench, level );
        }
    }
    
    public ItemStackBuilder addUnsafeEnchantment( Enchantment ench, int level ) {
        try {
            this.itemStack.addUnsafeEnchantment( ench, level );
        } catch ( Exception ex ) {
        }
        return this;
    }
    
    public ItemStackBuilderMeta getMeta() {
        return new ItemStackBuilderMeta( this, this.get().getItemMeta() );
    }
    
    public ItemStack get() {
        return this.itemStack;
    }
    
}
