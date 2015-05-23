package com.exorath.gameAPI.game.lobby;

import com.exorath.gameAPI.GameAPI;
import com.exorath.gameAPI.game.teams.TeamProperty;
import com.exorath.gameAPI.lib.Properties;

/**
 * Created by too on 23/05/2015.
 * Child of Properties with to simplify property access.
 */
public class LobbyProperties extends Properties {
    /**
     * Set a LobbyProperty with an extra layer of security
     *
     * @param key   The key you want to store the value in
     * @param value The value you want to store in the key.
     */
    public void set(LobbyProperty key, Object value) {
        //Compare object types
        if (!key.getType().getClass().isAssignableFrom(value.getClass())) {
            GameAPI.error("Tried to set LobbyProperty " + key + " with class: " + value.getClass() + " while it should be a " + key.getType());
            return;
        }
        set(key, value);
    }

    public Object get(LobbyProperty key) {
        return get(key.getKey(), null);
    }

    public Object get(LobbyProperty key, Object def) {
        return get(key.getKey(), def);
    }
}
