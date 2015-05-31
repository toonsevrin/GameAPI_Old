package com.yoshigenius.lib.util;

import org.bukkit.Material;

/**
 * @author Nick Robson
 */
public class ArmorEnums {
    
    public static enum ArmorType {
        
        BOOTS,
        HELMET,
        LEGGINGS,
        CHESTPLATE;
        
        public boolean isBetterThan( ArmorType type ) {
            return this.ordinal() >= type.ordinal();
        }
        
        public static ArmorType getType( Material mat ) {
            return ArmorType.valueOf( mat.name().split( "_" )[ 1 ] );
        }
        
    }
    
    public static enum ArmorMaterial {
        
        LEATHER,
        GOLD,
        CHAINMAIL,
        IRON,
        DIAMOND;
        
        public boolean isBetterThan( ArmorMaterial mat ) {
            return this.ordinal() >= mat.ordinal();
        }
        
        public static ArmorMaterial getType( Material mat ) {
            return ArmorMaterial.valueOf( mat.name().split( "_" )[ 0 ] );
        }
        
    }
    
    public static Material getMaterial( ArmorMaterial material, ArmorType type ) {
        return Material.valueOf( material.name() + "_" + type.name() );
    }
}
