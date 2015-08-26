package com.exorath.game.api.inventory;

import java.util.HashMap;
import java.util.Iterator;

import com.exorath.game.lib.json.JSONObject;

/**
 * Created by TOON on 8/25/2015.
 */
public class Inventory {
    private HashMap<Integer, Item> items = new HashMap<>();
    public Inventory(){

    }
    public HashMap<Integer, Item> getItems(){
        return items;
    }
    public JSONObject getJSON(){
        JSONObject obj = new JSONObject();
        HashMap<Integer, JSONObject> itemsJSON = new HashMap<>();
        for(Integer slot : items.keySet())
            itemsJSON.put(slot, items.get(slot).getJSON());
        obj.put("i", itemsJSON);
        return obj;
    }
    public static Inventory fromJSON(String json){
        Inventory i = new Inventory();
        JSONObject obj = new JSONObject(json);
        if(obj.has("i")){
            JSONObject itemsJSON = obj.getJSONObject("i");
            Iterator<String> keys = itemsJSON.keys();
            while(keys.hasNext()){
                String key = keys.next();
                i.getItems().put(Integer.valueOf(key), Item.fromJSON(itemsJSON.getJSONObject(key).toString()));
            }
        }

        return i;
    }

}
