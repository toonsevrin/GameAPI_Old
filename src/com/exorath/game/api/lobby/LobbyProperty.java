package com.exorath.game.api.lobby;

import com.exorath.game.api.BasePlayerProperty;
import com.exorath.game.api.Property;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default lobby property keys.
 */
public class LobbyProperty extends BasePlayerProperty {

    public static final Property

    SPAWN = Property.get("spawn", "Default spawn in lobby", null),
            WORLD = Property.get("world", "Lobby world. World named 'lobby' by default", "lobby"),
            ENABLED = Property.get("enabled", "Whether or not the lobby is enabled", true);

}
