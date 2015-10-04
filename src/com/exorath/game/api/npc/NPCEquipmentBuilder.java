package com.exorath.game.api.npc;

import org.bukkit.inventory.ItemStack;

/**
 * @author Nick Robson
 */
public final class NPCEquipmentBuilder {

    public static NPCEquipmentBuilder newBuilder() {
        return new NPCEquipmentBuilder();
    }

    private NPCEquipment equipment = new NPCEquipment();

    private NPCEquipmentBuilder() {
    }

    public NPCEquipment get() {
        return equipment;
    }

    public NPCEquipmentBuilder hand(ItemStack item) {
        equipment.setItemInHand(item);
        return this;
    }

    public NPCEquipmentBuilder helmet(ItemStack item) {
        equipment.setHelmet(item);
        return this;
    }

    public NPCEquipmentBuilder chestplate(ItemStack item) {
        equipment.setChestplate(item);
        return this;
    }

    public NPCEquipmentBuilder leggings(ItemStack item) {
        equipment.setLeggings(item);
        return this;
    }

    public NPCEquipmentBuilder boots(ItemStack item) {
        equipment.setBoots(item);
        return this;
    }

}
