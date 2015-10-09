package me.nickrobson.lib.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public enum EnchantmentType {

    SWORD,

    TOOL,

    BOW,

    FISHING_ROD,

    ARMOR_BOOTS,

    ARMOR_HELMET,

    ARMOR,

    ALL,

    OTHER;

    public static EnchantmentType getType(Enchantment ench) {
        if (ench == null || ench.equals(null)) {
            return EnchantmentType.OTHER;
        } else if (ench == Enchantment.DURABILITY) {
            return EnchantmentType.ALL;
        } else if (ench.canEnchantItem(new ItemStackBuilder(Material.BOW).get())) {
            return EnchantmentType.BOW;
        } else if (ench.canEnchantItem(new ItemStackBuilder(Material.WOOD_SWORD).get())) {
            return EnchantmentType.SWORD;
        } else if (ench.canEnchantItem(new ItemStackBuilder(Material.WOOD_PICKAXE).get())) {
            return EnchantmentType.TOOL;
        } else if (ench == Enchantment.OXYGEN || ench == Enchantment.WATER_WORKER) {
            return EnchantmentType.ARMOR_HELMET;
        } else if (ench == Enchantment.PROTECTION_FALL) {
            return EnchantmentType.ARMOR_BOOTS;
        } else if (ench.canEnchantItem(new ItemStackBuilder(Material.LEATHER_CHESTPLATE).get())) {
            return EnchantmentType.ARMOR;
        } else if (ench.canEnchantItem(new ItemStackBuilder(Material.FISHING_ROD).get())) {
            return EnchantmentType.FISHING_ROD;
        } else {
            return EnchantmentType.OTHER;
        }
    }

}
