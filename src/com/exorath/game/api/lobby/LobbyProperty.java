package com.exorath.game.api.lobby;

import com.exorath.game.api.Property;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default lobby property keys.
 */
public class LobbyProperty {
    
    public static final Property
            
            LOBBY_SPAWN = Property.get( "lobbyspawn", "Default spawn in lobby", null ),
            WORLD = Property.get( "world", "Lobby world. World named 'Lobby' by default", Lobby.DEFAULT_WORLD_NAME ),
            ENABLED = Property.get( "enabled", "Whether or not the lobby is enabled", true ),
            PVP = Property.get( "pvp", "Whether or not pvp is allowed in the lobby", false ),
            BLOCK_BREAK = Property.get( "blockbreak", "Whether or not block breaking is allowed in the lobby", false ),
            BLOCK_PLACE = Property.get( "blockplace", "Whether or not block placement is allowed in the lobby", false ),
            INTERACT = Property.get( "interact", "Whether or interaction is allowed in the lobby", false );
    
}
