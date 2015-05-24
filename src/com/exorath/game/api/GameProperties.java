package com.exorath.game.api;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.Properties;

/**
 * Created by too on 23/05/2015.
 * Extension of Properties to simplify Property access
 */
public class GameProperties extends Properties {
    /**
     * Set a GameProperty with another layer of security
     *
     * @param key   The key you want to store the value in
     * @param value The value you want to store in the key.
     */
    public void set(GameProperty key, Object value) {
        //Compare object types
        if (!key.getType().getClass().isAssignableFrom(value.getClass())) {
            GameAPI.error("Tried to set GameProperty " + key + " with class: " + value.getClass() + " while it should be a " + key.getType());
            return;
        }
        set(key, value);
    }

    public Object get(GameProperty key) {
        return get(key.getKey(), null);
    }

    public Object get(GameProperty key, Object def) {
        return get(key.getKey(), def);
    }
}
