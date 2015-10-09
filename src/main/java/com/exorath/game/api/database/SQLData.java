package com.exorath.game.api.database;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * Created by Toon Sevrin on 8/16/2015.
 */
@SuppressWarnings("unused")
public class SQLData {

    private static final Class<?>[] allowedTypes = new Class[] {
            String.class, Integer.class, Float.class, Double.class, Long.class, Date.class
    };

    private UUID uuid;
    private String tableName;

    private Map<String, Object> data = new HashMap<>();
    private boolean loaded = false;

    public SQLData(Plugin host, String tableName, UUID uuid, boolean sync) {
        this.uuid = uuid;
        tableName = host.getName() + "_" + tableName;//eg. GolemWarfare_Players

        load(sync);
    }

    //** Database manipulators **//
    /**
     * Loads data from database
     *
     * @param sync
     *            whether or not the data should be loaded synchronous with the
     *            main thread or not
     */
    private void load(boolean sync) {
        if (sync)
            new LoadTask().run();
        else
            new LoadTask().runTaskAsynchronously(GameAPI.getInstance());
    }

    /**
     * Reload data from database
     */
    public void reload(boolean sync) {
        data.clear();
        load(sync);
    }

    /**
     * Save data to database
     */
    public void save(boolean sync) {
        if (sync)
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
        List<String> keys = Lists.newArrayList(data.keySet());
        String k = Joiner.on(',').join(keys);
        String v = Joiner.on("','").join(keys.stream().map(s -> data.get(s)).collect(Collectors.toList()));
        if (v.length() > 0)
            v = "'" + v + "'";
        return "(" + k + ") VALUES (" + v + ")";
    }

    //** Setters **//
    public void setString(String key, String value) {
        if (key == null) {
            GameAPI.error("SQLData: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setInt(String key, int value) {
        if (key == null) {
            GameAPI.error("SQLData: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setFloat(String key, float value) {
        if (key == null) {
            GameAPI.error("SQLData: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setDouble(String key, double value) {
        if (key == null) {
            GameAPI.error("SQLData: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setLong(String key, long value) {
        if (key == null) {
            GameAPI.error("SQLData: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    public void setDate(String key, Date value) {
        if (key == null) {
            GameAPI.error("SQLData: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    protected void setData(String key, Object value) {
        if (key == null) {
            GameAPI.error("SQLData: tried to set key with null value");
            return;
        }
        data.put(key, value);
    }

    //** Getters **//
    /**
     * @return null if key is not found, otherwise the saved value
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * @return def if key is not found, otherwise the saved value
     */
    public String getString(String key, String def) {
        return data.containsKey(key) ? data.get(key).toString() : def;
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * @return def if key is not found, otherwise the saved value
     */
    public int getInt(String key, int def) {
        if (!data.containsKey(key))
            return def;
        Object value = data.get(key);
        if (!(value instanceof Number))
            return def;
        return ((Number) value).intValue();
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    /**
     * @return def if key is not found, otherwise the saved value
     */
    public float getFloat(String key, float def) {
        if (!data.containsKey(key))
            return def;
        Object value = data.get(key);
        if (!(value instanceof Number))
            return def;
        return ((Number) value).floatValue();
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
     * @return def if key is not found, otherwise the saved value
     */
    public double getDouble(String key, double def) {
        if (!data.containsKey(key))
            return def;
        Object value = data.get(key);
        if (!(value instanceof Number))
            return def;
        return ((Number) value).doubleValue();
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public long getLong(String key) {
        return getLong(key, 0);
    }

    /**
     * @return def if key is not found, otherwise the saved value
     */
    public long getLong(String key, long def) {
        if (!data.containsKey(key))
            return def;
        Object value = data.get(key);
        if (!(value instanceof Number))
            return def;
        return ((Number) value).longValue();
    }

    /**
     * @return null if key is not found, otherwise the saved value
     */
    public Date getDate(String key) {
        return getDate(key, null);
    }

    /**
     * @return def if key is not found, otherwise the saved value
     */
    public Date getDate(String key, Date def) {
        if (!data.containsKey(key))
            return def;
        Object value = data.get(key);
        if (!(value instanceof Date))
            return def;
        return (Date) value;
    }

    public boolean contains(String key) {
        if (key == null)
            return false;
        return data.containsKey(key);
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getTableName() {
        return tableName;
    }

    protected Map<String, Object> getData() {
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
            if (table == null || !table.rowExists(uuid.toString()))
                return;
            //Load all cells
            if (table.load(SQLData.this))
                setLoaded();
        }

        //Change loaded to true, needs testing if this happens before the login event
        private void setLoaded() {
            Bukkit.getScheduler().runTask(GameAPI.getInstance(), () -> loaded = true);
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

            if (table.rowExists(uuid.toString()))
                table.updateRow(SQLData.this);
            else {//create row
                //Create not existing columns
                for (String key : data.keySet()) {
                    if (table.getColumns().containsKey(key))
                        continue;
                    addColumn(table, key, data.get(key));
                }
                table.createRow(SQLData.this);
            }
        }

        private void addColumn(SQLTable table, String key, Object value) {
            if (value instanceof String)
                table.addColumn(key, ColumnType.STRING_128);
            else if (value instanceof Integer)
                table.addColumn(key, ColumnType.INT);
            else if (value instanceof Float)
                table.addColumn(key, ColumnType.FLOAT);
            else if (value instanceof Double)
                table.addColumn(key, ColumnType.DOUBLE);
            else if (value instanceof Long)
                table.addColumn(key, ColumnType.BIG_INT);
            else if (value instanceof Date)
                table.addColumn(key, ColumnType.DATE_TIME);
        }
    }
}
