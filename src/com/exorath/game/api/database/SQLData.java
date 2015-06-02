package com.exorath.game.api.database;


import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by toon on 31/05/2015.
 * This is a collection of data (Could be of a player, objective...).
 */
public class SQLData {
    private String dataKey;
    private ColumnType type;
    private HashMap<String, Object> data = new HashMap<String, Object>();
    public SQLData(String dataKey, ColumnType type){
        this.dataKey = dataKey;
        this.type = type;
    }
    public void addData(String key, Serializable value){

        data.put(key, value);
    }
    public Object getData(String key){
        if(data.containsKey(key)) return data.get(key);
        return null;
    }
    public HashMap<String, Object> getData(){
        return data;
    }

    /**
     * TODO: Nick has to check this method
     * @return
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

