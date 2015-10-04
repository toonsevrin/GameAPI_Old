package com.exorath.game.api.npc;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 * @author Nick Robson
 */
public class NPCEquipment {

    private ItemStack hand;
    private ItemStack[] armor = new ItemStack[4];

    public NPCEquipment() {
        this(new ItemStack(Material.AIR), new ItemStack[] {});
    }

    public NPCEquipment(ItemStack hand, ItemStack[] armor) {
        setItemInHand(hand);
        setArmorContents(armor);
    }

    public ItemStack getItemInHand() {
        return hand;
    }

    public void setItemInHand(ItemStack item) {
        hand = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack[] getArmorContents() {
        return armor;
    }

    public void setArmorContents(ItemStack[] armor) {
        int max = Math.min(armor.length, 4);// 0 <= x <= 4
        for (int i = 0; i < 4; i++)
            if (i < max)
                this.armor[i] = armor[i] == null ? new ItemStack(Material.AIR) : armor[i];
            else
                this.armor[i] = new ItemStack(Material.AIR);
    }

    public ItemStack getHelmet() {
        return armor[0];
    }

    public void setHelmet(ItemStack item) {
        armor[0] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack getChestplate() {
        return armor[1];
    }

    public void setChestplate(ItemStack item) {
        armor[1] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack getLeggings() {
        return armor[2];
    }

    public void setLeggings(ItemStack item) {
        armor[2] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack getBoots() {
        return armor[3];
    }

    public void setBoots(ItemStack item) {
        armor[3] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public void apply(LivingEntity entity) {
        try {
            EntityEquipment equipment = entity.getEquipment();
            equipment.setItemInHand(getItemInHand());
            equipment.setHelmet(getHelmet());
            equipment.setChestplate(getChestplate());
            equipment.setLeggings(getLeggings());
            equipment.setBoots(getBoots());
        } catch (Exception ex) {
        } // in case Bukkit throws errors when we try to get the equipment of a sheep, say. also for silent NPEs
    }
}
