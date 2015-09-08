package com.exorath.game.api.inventory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

import com.exorath.game.GameAPI;
import com.google.gson.JsonObject;

/**
 * Created by TOON on 8/25/2015.
 */
public class Inventory {

    private HashMap<Integer, Item> items = new HashMap<>();

    public Inventory() {

    }

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    public JsonObject getJSON() {
        JsonObject obj = new JsonObject();
        HashMap<Integer, JsonObject> itemsJSON = new HashMap<>();
        for (Integer slot : items.keySet())
            itemsJSON.put(slot, items.get(slot).getJSON());
        obj.add("i", GameAPI.GSON.toJsonTree(itemsJSON));
        return obj;
    }

    public static Inventory fromJSON(String json) {
        Inventory i = new Inventory();
        JsonObject obj = GameAPI.GSON.fromJson(json, JsonObject.class);
        if (obj.has("i")) {
            JsonObject itemsJSON = obj.getAsJsonObject("i");
            Iterator<String> keys = itemsJSON.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toSet())
                    .iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                i.getItems().put(Integer.valueOf(key), Item.fromJSON(itemsJSON.getAsJsonObject(key).toString()));
            }
        }

        return i;
    }

}
