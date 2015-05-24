package com.exorath.game.lib;

import java.util.HashMap;


/**
 * Created by too on 23/05/2015.
 * Properties are used to load and save certain settings within a session.
 * Note that we are not using child properties in code atm. This is because the Game object tree does the same thing.
 */

public class Properties {
    private HashMap<String, Object> properties;

    public Properties() {
        properties = new HashMap<String, Object>();
    }

    /**
     * @param key The property key
     * @return Returns the value out of this or out of the childs properties or null if value not found.
     */
    public Object get(String key) {
        return get(key, null);
    }

    /**
     * @param key The property key
     * @param def Default value to return if key not found
     * @return Returns the value out of properties or def if value not found.
     */
    public Object get(String key, Object def) {
        if (properties.containsKey(key))
            return properties.get(key);
        else return def;
    }

    /**
     * Set a property
     *
     * @param key   The key you want to store the value in
     * @param value The value you want to store in the key.
     */
    public void set(String key, Object value) {
        properties.put(key, value);
    }
}
