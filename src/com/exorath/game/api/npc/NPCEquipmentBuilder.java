package com.exorath.game.api.npc;

import org.bukkit.inventory.ItemStack;

/**
 * @author Nick Robson
 */
public final class NPCEquipmentBuilder {

    private NPCEquipment equipment = new NPCEquipment();

    NPCEquipmentBuilder() {
    }

    public NPCEquipment get() {
        return this.equipment;
    }

    public NPCEquipmentBuilder hand(ItemStack item) {
        this.equipment.setItemInHand(item);
        return this;
    }

    public NPCEquipmentBuilder helmet(ItemStack item) {
        this.equipment.setHelmet(item);
        return this;
    }

    public NPCEquipmentBuilder chestplate(ItemStack item) {
        this.equipment.setChestplate(item);
        return this;
    }

    public NPCEquipmentBuilder leggings(ItemStack item) {
        this.equipment.setLeggings(item);
        return this;
    }

    public NPCEquipmentBuilder boots(ItemStack item) {
        this.equipment.setBoots(item);
        return this;
    }

}
