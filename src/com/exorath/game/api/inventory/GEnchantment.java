package com.exorath.game.api.inventory;

import org.bukkit.enchantments.Enchantment;

/**
 * Created by Toon Sevrin on 8/25/2015.
 */
public class GEnchantment {
    private Enchantment enchantment;
    private int level;

    public GEnchantment(Enchantment enchantment, int level){

        this.enchantment = enchantment;
        this.level = level;
    }

    /* Enchantment */
    public Enchantment getEnchantment() {
        return enchantment;
    }
    public void setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
    }
    /* Level */
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
