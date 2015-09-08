package com.exorath.game.api.npc;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 * @author Nick Robson
 */
public class NPCEquipment {

    public static NPCEquipmentBuilder builder() {
        return new NPCEquipmentBuilder();
    }

    private ItemStack hand;
    private ItemStack[] armor = new ItemStack[4];

    public NPCEquipment() {
        this(new ItemStack(Material.AIR), new ItemStack[] {});
    }

    public NPCEquipment(ItemStack hand, ItemStack[] armor) {
        this.setItemInHand(hand);
        this.setArmorContents(armor);
    }

    public ItemStack getItemInHand() {
        return this.hand;
    }

    public void setItemInHand(ItemStack item) {
        this.hand = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack[] getArmorContents() {
        return this.armor;
    }

    public void setArmorContents(ItemStack[] armor) {
        int max = Math.min(armor.length, 4);// 0 <= x <= 4
        for (int i = 0; i < 4; i++) {
            if (i < max) {
                this.armor[i] = armor[i] == null ? new ItemStack(Material.AIR) : armor[i];
            } else {
                this.armor[i] = new ItemStack(Material.AIR);
            }
        }
    }

    public ItemStack getHelmet() {
        return this.armor[0];
    }

    public void setHelmet(ItemStack item) {
        this.armor[0] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack getChestplate() {
        return this.armor[1];
    }

    public void setChestplate(ItemStack item) {
        this.armor[1] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack getLeggings() {
        return this.armor[2];
    }

    public void setLeggings(ItemStack item) {
        this.armor[2] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public ItemStack getBoots() {
        return this.armor[3];
    }

    public void setBoots(ItemStack item) {
        this.armor[3] = item == null ? new ItemStack(Material.AIR) : item;
    }

    public void apply(LivingEntity entity) {
        try {
            EntityEquipment equipment = entity.getEquipment();
            equipment.setItemInHand(this.getItemInHand());
            equipment.setHelmet(this.getHelmet());
            equipment.setChestplate(this.getChestplate());
            equipment.setLeggings(this.getLeggings());
            equipment.setBoots(this.getBoots());
        } catch (Exception ex) {
        } // in case Bukkit throws errors when we try to get the equipment of a sheep, say. also for silent NPEs
    }
}
