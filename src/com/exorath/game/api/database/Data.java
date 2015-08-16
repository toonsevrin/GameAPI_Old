package com.exorath.game.api.database;

import com.exorath.game.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by TOON on 8/16/2015.
 */
public class Data {
    private static final Class[] allowedTypes = new Class[]{String.class, Integer.class, Float.class, Double.class, Long.class, Date.class};
    private UUID uuid;
    private String tableName;

    private HashMap<String, Object> data = new HashMap<>();
    private boolean loaded = false;

    public Data(Plugin host, String tableName, UUID uuid, boolean sync) {
        this.uuid = uuid;
        tableName = host.getName() + "_" + tableName; //eg. GolemWarfare_Players

        load(sync);
    }
    //** Database manipulators **//
    /**
     * Loads data from database
     * @param sync whether or not the data should be loaded synchronous with the main thread or not
     */
    private void load(boolean sync){
        if(sync)
            new LoadTask().run();
        else
            new LoadTask().runTaskAsynchronously(GameAPI.getInstance());
    }
    /**
     * Reload data from database
     */
    public void reload(boolean sync) {
        boolean loaded = false;
        data.clear();

        load(sync);
    }

    /**
     * Save data to database
     */
    public void save(boolean sync){
        if(sync)
            new SaveTask().run();
        else
            new SaveTask().runTaskAsynchronously(GameAPI.getInstance());
    }
    //** Utility **//
    /**
     * Get a string with keys and values formatted like this:
     * KEY1,KEY2,KEY3...='VALUE1','VALUE2','VALUE3'
     * TODO: Remove apostrophes if not a string.
     *
     * @return A string with keys and values, formatted for insert
     */
    public String getValuesString() {
        StringBuilder keys = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for ( String key : this.data.keySet() ) {
            keys.append( key );
            keys.append( "," );

            values.append( "'" );
            values.append( this.data.get( key ).toString() );
            values.append( "'" );
            values.append( "," );
        }
        keys.deleteCharAt( keys.length() - 1 );
        values.deleteCharAt( keys.length() - 1 );

        return "(" + keys.toString() + ") VALUES (" + values.toString() + ")";
    }
    //** Setters **//
    public void setString(String key, String value) {
        if (key == null) {
            GameAPI.error("Data: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setInt(String key, int value) {
        if (key == null) {
            GameAPI.error("Data: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setFloat(String key, float value) {
        if (key == null) {
            GameAPI.error("Data: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setDouble(String key, double value) {
        if (key == null) {
            GameAPI.error("Data: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setLong(String key, long value) {
        if (key == null) {
            GameAPI.error("Data: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setDate(String key, Date value) {
        if (key == null) {
            GameAPI.error("Data: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    protected void setData(String key, Object value){
        if (key == null) {
            GameAPI.error("Data: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }
    //** Getters **//

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public String getString(String key) {
        if (!data.containsKey(key))
            return null;
        Object value = data.get(key);
        if (!(value instanceof String))
            return null;
        return (String) value;
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public Integer getInt(String key) {
        if (!data.containsKey(key))
            return null;
        Object value = data.get(key);
        if (!(value instanceof Integer))
            return null;
        return (Integer) value;
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public Float getFloat(String key) {
        if (!data.containsKey(key))
            return null;
        Object value = data.get(key);
        if (!(value instanceof Float))
            return null;
        return (Float) value;
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public Double getDouble(String key) {
        if (!data.containsKey(key))
            return null;
        Object value = data.get(key);
        if (!(value instanceof Double))
            return null;
        return (Double) value;
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public Long getLong(String key) {
        if (!data.containsKey(key))
            return null;
        Object value = data.get(key);
        if (!(value instanceof Long))
            return null;
        return (Long) value;
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public Date getDate(String key) {
        if (!data.containsKey(key))
            return null;
        Object value = data.get(key);
        if (!(value instanceof Date))
            return null;
        return (Date) value;
    }


    public boolean contains(String key){
        if(key == null)
            return false;
        return data.containsKey(key);
    }
    public UUID getUuid(){
        return uuid;
    }
    public String getTableName(){
        return tableName;
    }
    protected HashMap<String, Object> getData(){
        return data;
    }
    /**
     * Loads data from database into this object
     */
    private class LoadTask extends BukkitRunnable {
        @Override
        public void run() {
            //Get the table
            SQLTable table = GameAPI.getSQLManager().getTable(tableName);
            //Check if uuid exists
            if(!table.rowExists(uuid.toString()))
                return;
            //Load all cells
            if(table.load(Data.this))
                setLoaded();
        }

        //Change loaded to true, needs testing if this happens before the login event
        private void setLoaded() {
            Bukkit.getScheduler().runTask(GameAPI.getInstance(), new Runnable() {
                @Override
                public void run() {
                    loaded = true;
                }
            });
        }
    }
    /**
     * Saves data from database into this object
     */
    private class SaveTask extends BukkitRunnable {
        @Override
        public void run() {
            //Get the table
            SQLTable table = GameAPI.getSQLManager().getTable(tableName);

            if(table.rowExists(uuid.toString())) {//Update row
                table.updateRow(Data.this);
            } else{//create row
                //Create not existing columns
                for(String key : data.keySet()){
                    if(table.getColumns().containsKey(key))
                        continue;
                    addColumn(table, key, data.get(key));
                }
                table.createRow(Data.this);
            }
        }
        private void addColumn(SQLTable table, String key, Object value){
            if(value instanceof String){
                table.addColumn(key, ColumnType.STRING_128);
            }else if(value instanceof Integer){
                table.addColumn(key, ColumnType.INT);
            }else if(value instanceof Float){
                table.addColumn(key, ColumnType.FLOAT);
            }else if(value instanceof Double){
                table.addColumn(key, ColumnType.DOUBLE);
            }else if(value instanceof Long){
                table.addColumn(key, ColumnType.BIG_INT);
            }else if(value instanceof Date){
                table.addColumn(key, ColumnType.DATE_TIME);
            }
        }
    }
}
