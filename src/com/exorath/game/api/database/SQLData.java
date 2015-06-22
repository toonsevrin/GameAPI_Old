package com.exorath.game.api.database;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created by toon on 31/05/2015.
 * This is a collection of data (Could be of a player, objective...).
 */
public class SQLData {
    
    private String dataKey;
    private Map<String, Object> data = Maps.newHashMap();
    
    public SQLData( String dataKey ) {
        this.dataKey = dataKey;
    }
    
    public String getKey() {
        return this.dataKey;
    }
    
    /**
     * Add data to this data collection
     * 
     * @param key
     *            Data key
     * @param value
     *            Data value
     */
    public void addData( String key, Object value ) {
        this.data.put( key, value );
    }
    
    /**
     * Get the value of a certain key
     * 
     * @param key
     *            Key that is linked to the value
     * @return Value linked to the key, null if it doesn't exist
     */
    public Object getData( String key ) {
        if ( this.data.containsKey( key ) ) {
            return this.data.get( key );
        }
        return null;
    }
    
    public Map<String, Object> getData() {
        return this.data;
    }
    
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
}
