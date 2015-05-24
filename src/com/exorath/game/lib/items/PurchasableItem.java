package com.exorath.game.lib.items;

import com.exorath.game.api.players.GPlayer;
import com.exorath.game.lib.Rank;
import com.exorath.game.lib.UnlockRequirements;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by too on 24/05/2015.
 * A simpler way to generate a purchasable item, this always requires complex and ugly methods so it's better to do once.
 */
public class PurchasableItem extends ItemStack {
    private GPlayer player;
    private String name;
    private Material material;
    private String[] description;
    private UnlockRequirements requirements;

    public PurchasableItem(String name, Material material, String[] description, UnlockRequirements requirements, GPlayer player, boolean unlocked) {
        this.name = name;
        this.material = material;
        this.description = description;
        this.requirements = requirements;
        this.player = player;
        buildItem(unlocked);
    }

    /**
     * Update the ItemStack
     * @param unlocked Whether the item has been unlocked already
     */
    public void update(boolean unlocked){
        buildItem(unlocked);
    }
    public void update(){
        buildItem();
    }

    private void buildItem(){
        buildItem(false);
    }
    /**
     * Generate a purchasable ItemStack with possible cost and restriction
     * Ugly method handy to avoid repetition
     * TODO: Add possible amount support
     * @param unlocked Whether the item has been unlocked already
     */
    private void buildItem(boolean unlocked) {
        ItemStackBuilder builder = new ItemStackBuilder(this);
        ItemStackBuilder.ItemStackBuilderMeta meta = builder.getMeta();
        meta.setLore(description);
        meta.addLore("");
        if (unlocked) {
            meta.setDisplayName(ChatColor.BOLD.toString() + ChatColor.GREEN + name);
        } else {
            meta.setDisplayName(getRedOrGreen(ChatColor.BOLD + name, requirements.canUnlock(player)));
            if (requirements.getHonorPoints() > 0) {
                meta.addLore(getRedOrGreen("Cost: " + requirements.getHonorPoints() + " honor points", player.hasHonorPoints(requirements.getHonorPoints())));
            }
            if (requirements.getCredits() > 0) {
                meta.addLore(getRedOrGreen("Cost: " + requirements.getCredits() + " credits", player.hasCredits(requirements.getCredits())));
            }
            if (requirements.getMinRank() != Rank.NONE) {
                meta.addLore(getRedOrGreen(ChatColor.BOLD + requirements.getMinRank().getName() + " required", player.getRank().inheritsFrom(requirements.getMinRank())));
            }
        }
    }

    private static String getRedOrGreen(String line, boolean green) {
        if (green) return ChatColor.GREEN + line;
        return ChatColor.RED + line;
    }

    public UnlockRequirements getRequirements() {
        return requirements;
    }

    public void setRequirements(UnlockRequirements requirements) {
        this.requirements = requirements;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
