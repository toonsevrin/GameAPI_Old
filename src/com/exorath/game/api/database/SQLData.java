package com.exorath.game.api.database;


import java.io.Serializable;
import java.util.HashMap;

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

    /**
     * Add data to this data collection
     * @param key Data key
     * @param value Data value
     */
    public void addData(String key, Object value){
        data.put(key, value);
    }

    /**
     * Get the value of a certain key
     * @param key Key that is linked to the value
     * @return Value linked to the key, null if it doesn't exist
     */
    public Object getData(String key){
        if(data.containsKey(key)) return data.get(key);
        return null;
    }
    public HashMap<String, Object> getData(){
        return data;
    }

    /**
     * Get a string with keys and values formatted like this: KEY1,KEY2,KEY3...='VALUE1','VALUE2','VALUE3'
     * TODO: Remove apostrophes if not a string.
     * @return A string with keys and values, formatted for insert
     */
    public String getValuesString(){
        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for(String key : data.keySet()){
            keys.append(key);
            keys.append(",");

            values.append("'");
            values.append(data.get(key).toString());
            values.append("'");
            values.append(",");
        }
        keys.deleteCharAt(keys.length() - 1);
        values.deleteCharAt(keys.length() - 1);

        return "(" + keys.toString() + ") VALUES (" + values.toString() + ")";
    }
}

