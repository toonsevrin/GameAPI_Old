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

/**
 * A builder class to wrap {@link ItemStack}.
 *
 * @author Nick Robson
 */
public class ItemStackBuilder {

    /**
     * A builder class to wrap {@link ItemMeta}.
     *
     * @author Nick Robson
     */
    public static class ItemStackBuilderMeta {

        private final ItemStackBuilder builder;
        private final ItemMeta meta;

        protected ItemStackBuilderMeta( ItemStackBuilder builder, ItemMeta meta ) {
            this.builder = builder;
            this.meta = meta;
        }

        /**
         * Adds a line of lore.
         *
         * @param lore
         *            The lore.
         * @return This object.
         */
        public ItemStackBuilderMeta addLore( String lore ) {
            List<String> lores = this.meta.getLore();
            if ( lores == null ) {
                lores = new ArrayList<String>();
            }
            lores.add( lore );
            return this.setLore( lores );
        }

        /**
         * Adds multiple lines of lore.
         *
         * @param lines
         *            The lore.
         * @return This object.
         */
        public ItemStackBuilderMeta addPage( String[] lines ) {
            if ( this.meta instanceof BookMeta ) {
                BookMeta bookmeta = (BookMeta) this.meta;
                bookmeta.addPage( lines );
            }
            return this;
        }

        /**
         * Adds a stored enchantment if this is a Enchanted Book.
         *
         * @param ench
         *            The enchantment.
         * @param level
         *            The level.
         * @param show
         *            Whether or not to show it.
         * @return This object.
         */
        public ItemStackBuilderMeta addStoredEnchantment( Enchantment ench, int level, boolean show ) {
            if ( this.meta instanceof EnchantmentStorageMeta ) {
                EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) this.meta;
                enchmeta.addStoredEnchant( ench, level, show );
            }
            return this;
        }

        /**
         * Clears the lore on this item.
         *
         * @return This object.
         */
        public ItemStackBuilderMeta clearLore() {
            return this.setLore( new ArrayList<String>() );
        }

        /**
         * Gets the parent {@link ItemStackBuilder} object.
         *
         * @return The parent builder.
         */
        public ItemStackBuilder getBuilder() {
            this.builder.get().setItemMeta( this.meta );
            return this.builder;
        }

        /**
         * Gets all stored enchantments if this is an Enchanted Book.
         *
         * @return The stored enchantments.
         */
        public Map<Enchantment, Integer> getStoredEnchantments() {
            if ( this.meta instanceof EnchantmentStorageMeta ) {
                EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) this.meta;
                return enchmeta.getStoredEnchants();
            }
            return null;
        }

        /**
         * Sets the color if this is leather armour.
         *
         * @param color
         *            The new color.
         * @return This object.
         */
        public ItemStackBuilderMeta setColor( Color color ) {
            if ( this.meta instanceof LeatherArmorMeta ) {
                LeatherArmorMeta lameta = (LeatherArmorMeta) this.meta;
                lameta.setColor( color );
            }
            return this;
        }

        /**
         * Sets the display name of this item.
         *
         * @param display
         *            The new display name.
         * @return This object.
         */
        public ItemStackBuilderMeta setDisplayName( String display ) {
            this.meta.setDisplayName( display );
            return this;
        }

        /**
         * Sets the lore of this item.
         *
         * @param lore
         *            The lore.
         * @return This object.
         */
        public ItemStackBuilderMeta setLore( List<String> lore ) {
            this.meta.setLore( lore );
            return this;
        }

        /**
         * Sets the lore of this item.
         *
         * @param lore
         *            The lore.
         * @return This object.
         */
        public ItemStackBuilderMeta setLore( String... lore ) {
            return this.setLore( Arrays.asList( lore ) );
        }

        /**
         * Sets the owner if this is a Skull (to display the skin).
         *
         * @param owner
         *            The owner.
         * @return This object.
         */
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

        /**
         * Sets the page at the specified index to have the specified text if this is a Written
         * Book.
         *
         * @param index
         *            The index.
         * @param page
         *            The page text.
         * @return This object.
         */
        public ItemStackBuilderMeta setPage( int index, String page ) {
            if ( this.meta instanceof BookMeta ) {
                BookMeta bookmeta = (BookMeta) this.meta;
                bookmeta.setPage( index, page );
            }
            return this;
        }

        /**
         * Sets the title if this is a Written Book.
         *
         * @param title
         *            The new title.
         * @return This object.
         */
        public ItemStackBuilderMeta setTitle( String title ) {
            if ( this.meta instanceof BookMeta ) {
                BookMeta bookmeta = (BookMeta) this.meta;
                bookmeta.setTitle( title );
            }
            return this;
        }

    }

    private final ItemStack itemStack;

    /**
     * Creates a builder wrapping the provided {@link ItemStack}.
     *
     * @param base
     *            The item stack.
     */
    public ItemStackBuilder( ItemStack base ) {
        this. itemStack = base;
    }

    /**
     * Creates a builder wrapping an object with the provided {@link Material}.
     *
     * @param m
     *            The material.
     */
    public ItemStackBuilder( Material m ) {
        this( m, 1 );
    }

    /**
     * Creates a builder wrapping an object with the provided {@link Material}.
     *
     * @param m
     *            The material.
     * @param data
     *            The data.
     */
    public ItemStackBuilder( Material m, byte data ) {
        this( m, 1, data );
    }

    /**
     * Creates a builder wrapping an object with the provided {@link Material} and amount.
     *
     * @param m
     *            The material.
     * @param num
     *            The amount.
     */
    public ItemStackBuilder( Material m, int num ) {
        this( m, num, (byte) 0 );
    }

    /**
     * Creates a builder wrapping an object with the provided {@link Material}, amount and data.
     *
     * @param m
     *            The material.
     * @param num
     *            The amount.
     * @param data
     *            The data.
     */
    public ItemStackBuilder( Material m, int num, byte data ) {
        this( new ItemStack( m, num, data ) );
    }

    /**
     * Creates a builder wrapping an object with the provided {@link Material}, amount and data.
     *
     * @param m
     *            The material.
     * @param num
     *            The amount.
     * @param damage
     *            The damage value.
     */
    public ItemStackBuilder( Material m, int num, short damage ) {
        this( new ItemStack( m, num, damage ) );
    }

    /**
     * Adds an enchantment safely to this item.
     *
     * @param ench
     *            The enchantment.
     * @param level
     *            The level.
     * @param force
     *            Whether or not to force the enchantment onto the item.
     * @return This object.
     */
    public ItemStackBuilder addEnchantment( Enchantment ench, int level, boolean force ) {
        try {
            this.itemStack.addEnchantment( ench, level );
            return this;
        } catch ( Exception ex ) {
            if ( force ) {
                return this.addUnsafeEnchantment( ench, level );
            }
            return this;
        }
    }

    /**
     * Adds an enchantment unsafely to this item.
     *
     * @param ench
     *            The enchantment.
     * @param level
     *            The level.
     * @return This object.
     */
    public ItemStackBuilder addUnsafeEnchantment( Enchantment ench, int level ) {
        try {
            this.itemStack.addUnsafeEnchantment( ench, level );
        } catch ( Exception ex ) {}
        return this;
    }

    /**
     * Gets the wrapped {@link ItemStack}.
     *
     * @return The itemstack.
     */
    public ItemStack get() {
        return this.itemStack;
    }

    /**
     * Gets this item's meta as a {@link ItemStackBuilderMeta} object.
     *
     * @see ItemStackBuilderMeta#getBuilder()
     * @return The meta.
     */
    public ItemStackBuilderMeta getMeta() {
        return new ItemStackBuilderMeta( this, this.get().getItemMeta() );
    }

    /**
     * Sets the amount of items in this item stack.
     *
     * @param num
     *            The new amount.
     * @return This object.
     */
    public ItemStackBuilder setAmount( int num ) {
        this.itemStack.setAmount( num );
        return this;
    }

    /**
     * Sets the material of this item in this item stack.
     *
     * @param m
     *            The new material.
     * @return This object.
     */
    public ItemStackBuilder setMaterial( Material m ) {
        this.itemStack.setType( m );
        return this;
    }

}
