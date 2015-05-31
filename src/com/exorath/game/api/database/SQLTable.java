package com.exorath.game.api.database;

/**
 * Created by too on 31/05/2015.
 */
public class SQLTable {
    public SQLTable(){

    }

    /**
     * Get the data of the given key in this database
     * @param key Key to receive data from
     * @return data of key, null if it doesn't exist.
     */
    public SQLData getData(String key){
        return null;
    }
    public void setData(String key, SQLData data){

    }
    public void addColumn(String columnName, ColumnType type){
        addColumn(columnName, type.getName());
    }
    public void addColumn(String columnName, String type){

    }
}
