package com.exorath.game.api.database;

import com.exorath.game.GameAPI;

/**
 * Created by too on 31/05/2015.
 */
public class SQLTable {
    public static final String KEY = "Key";
    private String name;
    public SQLTable(String name){
        this.name = name;
    }

    /**
     * Get the data of the given key in this database
     * @param key Key to receive data from
     * @return data of key, null if it doesn't exist.
     */
    public SQLData getData(String key){
        //TODO: If exists: load and return. Else: return null;
        return null;
    }
    public void setData(String key, SQLData data){
        //TODO: If exists: update. Else: Insert.
    }
    public void addColumn(String columnName, ColumnType type){
        addColumn(columnName, type.getName());
    }
    private void addColumn(String columnName, String type){
        GameAPI.getSQLManager().executeQuery("ALTER TABLE " + name + " ADD " + columnName + " " + type);
    }
    private void refresh(){
        GameAPI.getSQLManager().refresh();
    }
}
