package com.exorath.game.api.inventory;

import com.exorath.game.lib.json.JSONArray;
import com.exorath.game.lib.json.JSONObject;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

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

    public JSONObject getJSON() {
        if(amount == 0)
            return null;

        JSONObject obj = new JSONObject();

        Map<String, Integer> enchs = new HashMap<>();
        for(GEnchantment enchantment : enchantments)
            enchs.put("ENCHANTMENT", enchantment.getLevel());

        if(name != null)
            obj.put("n", name);
        if(material != null)
            obj.put("m", material.toString());
        obj.put("a", amount);
        if(durability != 0)
            obj.put("d", durability);
        if(enchs.size() != 0)
            obj.put("e",enchs);
        if(lore.size() != 0)
            obj.put("l", lore);

        return obj;
    }
    public static Item fromJSON(String json){
        Item item = new Item();
        JSONObject obj = new JSONObject(json);
        if(obj.has("n"))
            item.setName(obj.getString("n"));
        if(obj.has("m"))
            item.setMaterial(Material.valueOf(obj.getString("m")));
        if(obj.has("d"))
            item.setDurability(obj.getInt("d"));
        if(obj.has("a"))
            item.setAmount(obj.getInt("a"));
        if(obj.has("l")) {
            JSONArray arr = obj.getJSONArray("l");
            List<String> lore = new ArrayList<String>();
            for(int i = 0; i < arr.length(); i++){
                lore.add(arr.getString(i));
            }
            item.setLore(lore);
        }
        if(obj.has("e")) {
            JSONObject enchJSON = obj.getJSONObject("e");
            Set<GEnchantment> enchantments = new HashSet<GEnchantment>();

            Iterator<String> keys = enchJSON.keys();
            while(keys.hasNext()){

                String key = keys.next();
                item.addEnchantment(new GEnchantment(Enchantment.getByName(key), enchJSON.getInt(key)));
            }
        }
        return item;
    }
}