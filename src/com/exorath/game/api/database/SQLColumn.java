package com.exorath.game.api.database;

/**
 * Created by too on 1/06/2015.
 */
public class SQLColumn {
    private String key;
    private ColumnType type;
    public SQLColumn(String key, ColumnType type){
        this.key = key;
        this.type = type;
    }
    public String getKey(){
        return key;
    }
    public ColumnType getType(){
        return type;
    }
}
