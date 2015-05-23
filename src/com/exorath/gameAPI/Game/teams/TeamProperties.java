package com.exorath.gameAPI.game.teams;

import com.exorath.gameAPI.GameAPI;
import com.exorath.gameAPI.lib.Properties;

/**
 * Created by too on 23/05/2015.
 * Child of Properties with to simplify property access.
 */
public class TeamProperties extends Properties {
    /**
     * Set a TeamProperty with an extra layer of security
     *
     * @param key   The key you want to store the value in
     * @param value The value you want to store in the key.
     */
    public void set(TeamProperty key, Object value) {
        //Compare object types
        if (!key.getType().getClass().isAssignableFrom(value.getClass())) {
            GameAPI.error("Tried to set TeamProperty " + key + " with class: " + value.getClass() + " while it should be a " + key.getType());
            return;
        }
        set(key, value);
    }

    public Object get(TeamProperty key) {
        return get(key.getKey(), null);
    }

    public Object get(TeamProperty key, Object def) {
        return get(key.getKey(), def);
    }
}
