package com.exorath.game.api.database;

import com.exorath.game.GameAPI;

import java.sql.ResultSet;
import java.sql.SQLException;

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

        if(rowExists(key)){
            createRow(key, data);
        }else{
            updateRow(key, data);
        }
    }
    public void createRow(String key, SQLData data){
        //create new row with data
    }
    public void updateRow(String key, SQLData data){
        //update row with data
    }
    public boolean rowExists(String key){
        try {
            ResultSet rs = GameAPI.getSQLManager().executeQuery("SELECT EXISTS(SELECT 1 FROM" + name + " WHERE " + KEY + "='" + key + "')");
            if (rs == null) return false;
            if (rs.next()) return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;

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
