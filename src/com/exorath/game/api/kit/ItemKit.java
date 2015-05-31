package com.exorath.game.api.kit;

import com.exorath.game.api.events.GPlayerRespawnEvent;
import com.exorath.game.api.events.GameStartEvent;
import com.exorath.game.api.players.GPlayer;
import net.minecraft.server.v1_8_R1.Item;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by too on 27/05/2015.
 */
public class ItemKit extends Kit{
    private Set<ItemStack> items = new HashSet<ItemStack>();
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    public ItemKit( String name, Material material, String[] description) {
        super(name, material);
        setDescription(description);
    }
    @Override
     public void onGameStart(GPlayer player) {
        setupPlayer(player);
    }
    //TODO: Add option in player properties to disable item receivement
    @Override
    public void onRespawn(GPlayerRespawnEvent event) {
        setupPlayer(event.getPlayer());
    }
    private void setupPlayer(GPlayer player){
        PlayerInventory inv = player.getBukkitPlayer().getInventory();
        if(helmet != null) inv.setHelmet(helmet);
        if(chestplate != null) inv.setChestplate(chestplate);
        if(leggings != null) inv.setLeggings(leggings);
        if(boots != null) inv.setBoots(boots);

        for(ItemStack item : items){
            inv.addItem(item);
        }
    }
    //Item management
    public void addItem(ItemStack stack){
        items.add(stack);
    }
    public void setHelmet(ItemStack helmet){
        this.helmet = helmet;
    }
    public void setChestplate(ItemStack chestplate){
        this.chestplate = chestplate;
    }
    public void setLeggings(ItemStack leggings){
        this.leggings = leggings;
    }
    public void setBoots(ItemStack boots){
        this.boots = boots;
    }
    public ItemStack[] getArmor(){
        return new ItemStack[]{helmet, chestplate, leggings, boots};
    }
    public Set<ItemStack> getItems(){
        return items;
    }
}
