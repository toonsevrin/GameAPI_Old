package com.exorath.game.api.kits;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.Properties;

/**
 * Created by too on 23/05/2015.
 * Child of Properties with to simplify property access.
 */
public class KitProperties extends Properties {
    /**
     * Set a KitProperties with an extra layer of security
     *
     * @param key   The key you want to store the value in
     * @param value The value you want to store in the key.
     */
    public void set(KitProperty key, Object value) {
        //Compare object types
        if (!key.getType().getClass().isAssignableFrom(value.getClass())) {
            GameAPI.error("Tried to set KitProperties " + key + " with class: " + value.getClass() + " while it should be a " + key.getType());
            return;
        }
        set(key, value);
    }

    public Object get(KitProperty key) {
        return get(key.getKey(), null);
    }

    public Object get(KitProperty key, Object def) {
        return get(key.getKey(), def);
    }
}
