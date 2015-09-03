package com.exorath.game.api.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import com.exorath.game.GameAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by TOON on 8/25/2015.
 */
public class Item {
    private String name;
    private Material material;
    private int amount = 1;
    private int durability = 0;
    private List<String> lore = new ArrayList<>();

    private Set<GEnchantment> enchantments = new HashSet<>();
    /* Name */
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /* Lore */
    public List<String> getLore() {
        return lore;
    }
    public void setLore(List<String> lore) {
        this.lore = lore;
    }
    /* Material */
    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
        this.material = material;
    }
    /* Enchantments */
    public Set<GEnchantment> getEnchantments() {
        return enchantments;
    }
    public void setEnchantments(Set<GEnchantment> enchantments) {
        this.enchantments = enchantments;
    }
    public void addEnchantment(GEnchantment enchantment){
        enchantments.add(enchantment);
    }
    public void removeEnchantment(GEnchantment enchantment){
        enchantments.remove(enchantment);
    }
    /* Amount */
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    /* Durability */
    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }

    public JsonObject getJSON() {
        if(amount == 0)
            return null;

        JsonObject obj = new JsonObject();

        Map<String, Integer> enchs = new HashMap<>();
        for(GEnchantment enchantment : enchantments)
            enchs.put("ENCHANTMENT", enchantment.getLevel());

        if(name != null)
            obj.addProperty( "n", name );
        if(material != null)
            obj.addProperty( "m", material.toString() );
        obj.addProperty( "a", amount );
        if(durability != 0)
            obj.addProperty( "d", durability );
        if(enchs.size() != 0)
            obj.add( "e", GameAPI.GSON.toJsonTree( enchs ) );
        if(lore.size() != 0)
            obj.add( "l", GameAPI.GSON.toJsonTree( enchs ) );

        return obj;
    }

    public static Item fromJSON(String json){
        Item item = new Item();
        JsonObject obj = GameAPI.GSON.fromJson( json, JsonObject.class );
        if(obj.has("n"))
            item.setName( obj.get( "n" ).getAsString() );
        if(obj.has("m"))
            item.setMaterial( Material.valueOf( obj.get( "m" ).getAsString() ) );
        if(obj.has("d"))
            item.setDurability( obj.get( "d" ).getAsInt() );
        if(obj.has("a"))
            item.setAmount( obj.get( "a" ).getAsInt() );
        if(obj.has("l")) {
            JsonArray arr = obj.getAsJsonArray( "l" );
            List<String> lore = new ArrayList<String>();
            for ( int i = 0; i < arr.size(); i++ ) {
                lore.add( arr.get( i ).getAsString() );
            }
            item.setLore(lore);
        }
        if(obj.has("e")) {
            JsonObject enchJSON = obj.getAsJsonObject( "e" );
            Iterator<String> keys = enchJSON.entrySet().stream().map( e -> e.getKey() ).collect( Collectors.toSet() ).iterator();
            while(keys.hasNext()){
                String key = keys.next();
                item.addEnchantment( new GEnchantment( Enchantment.getByName( key ), enchJSON.get( key ).getAsInt() ) );
            }
        }
        return item;
    }
}