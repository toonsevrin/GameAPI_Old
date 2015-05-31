package com.exorath.game.api.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by toon on 31/05/2015.
 * This is a collection of data (Could be of a player, objective...).
 */
public class SQLData {
    private String dataKey;
    private HashMap<String, Object> data = new HashMap<String, Object>();
    public SQLData(String dataKey){
        this.dataKey = dataKey;
    }
    public void addData(String key, Object value){
        data.put(key, value);
    }
    public Object getData(String key){
        if(data.containsKey(key)) return data.get(key);
        return null;
    }
    public HashMap<String, Object> getData(){
        return data;
    }
}
