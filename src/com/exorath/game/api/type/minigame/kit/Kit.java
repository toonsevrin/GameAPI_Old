package com.exorath.game.api.type.minigame.kit;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.Properties;
import com.exorath.game.api.npc.NPCEquipment;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.UnlockRequirements;
import com.exorath.game.lib.items.PurchasableItem;
import com.google.common.collect.Sets;
import com.yoshigenius.lib.util.ItemStackBuilder;
import com.yoshigenius.lib.util.RegexUtil;

/**
 * Created by too on 23/05/2015.
 * Base class for all GPlayer kits.
 */
public abstract class Kit {

    public static final String DEFAULT_NAME = "Kit";
    public static final ItemStack DEFAULT_ICON = new ItemStack(Material.WORKBENCH);
    public static final String[] DEFAULT_DESCRIPTION = new String[] { ChatColor.WHITE + "Default kit description" };

    private Properties properties = new Properties();
    private final Set<GameListener> listeners = Sets.newHashSet();

    public Kit(String name, ItemStack item) {
        this.setName(name);
        this.setIcon(item);
    }

    public void setName(String name) {
        this.properties.set(KitProperty.NAME, name);
    }

    public String getName() {
        return this.properties.as(KitProperty.NAME, String.class);
    }

    public void setIcon(ItemStack item) {
        this.properties.set(KitProperty.ICON, item);
    }

    public ItemStack getIcon() {
        return this.properties.as(KitProperty.ICON, ItemStack.class);
    }

    public void setDescription(String[] description) {
        this.properties.set(KitProperty.DESCRIPTION, description);
    }

    public String[] getDescription() {
        return this.properties.as(KitProperty.DESCRIPTION, String[].class);
    }

    public UnlockRequirements getUnlockRequirements() {
        return this.properties.as(KitProperty.UNLOCK_REQUIREMENTS, UnlockRequirements.class);
    }

    public void setUnlockRequirements(UnlockRequirements reqs) {
        this.properties.set(KitProperty.UNLOCK_REQUIREMENTS, reqs);
    }

    public PurchasableItem getKitItem(GamePlayer player) {
        return new PurchasableItem(this.getName(), this.getIcon(), this.getDescription(), this.getUnlockRequirements(),
                player,
                this.isUnlocked(player));
    }

    /**
     * Gets the itemStack for this kit to be displayed in the shop inventory
     *
     * @return itemStack with nice markup
     */
    public ItemStack getKitSelectorItem(GamePlayer player) {
        String name = this.getName();
        if (name == null || name.equals(" ")) {
            Pattern p = RegexUtil.UPPERCASE_LETTERS;
            String temp = name;
            Matcher matcher = p.matcher(this.getClass().getSimpleName());
            int index = 0;
            int numMatches = 0;
            while (matcher.find(index)) {
                index = matcher.start();
                temp = (index > 0 ? temp.substring(0, index + numMatches) + " " : "")
                        + temp.substring(index + numMatches);
                numMatches++;
            }
        }
        return new ItemStackBuilder(this.getIcon()).getMeta().setDisplayName(ChatColor.GOLD + name).getBuilder().get();
    }

    public abstract Map<Integer, ItemStack> getItems();

    public abstract Map<Integer, ItemStack> getArmour();

    public abstract Map<PotionEffectType, Integer> getPotionEffects();

    public void give(GamePlayer player, Game game) {
        //TODO: Go into GPlayer and add methods addKit, delKit, clearKits and getKits.
    }

    public boolean isUnlocked(GamePlayer player) {
        UnlockRequirements reqs = this.getUnlockRequirements();
        if (reqs == null) {
            return true;
        }
        return reqs.isUnlocked(player);
    }

    protected void addListener(GameListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    /* End Events */

    public static String greenOrRedString(String name, boolean green) {
        return (green ? ChatColor.GREEN : ChatColor.RED) + name;
    }

    public NPCEquipment toNPCEquipment() {
        NPCEquipment eq = new NPCEquipment();
        eq.setItemInHand(this.getIcon());
        eq.setHelmet(this.getArmour().get(0));
        eq.setChestplate(this.getArmour().get(1));
        eq.setLeggings(this.getArmour().get(2));
        eq.setBoots(this.getArmour().get(3));
        return eq;
    }
}
