package com.exorath.game.api.players;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by too on 23/05/2015.
 * Base player object in GameAPI
 */
public class GAPIPlayer {
    private Player player;
    private UUID uuid;
    private PlayerProperties properties;

    /**
     * This might be handy for pre-loading a player onPreJoin for example
     */
    public GAPIPlayer(){
        init();
    }
    /**
     * This might be handy for pre-loading a player onPreJoin for example
     */
    public GAPIPlayer(UUID id){
        this.uuid = uuid;
        init();
    }
    public GAPIPlayer(Player player){
        this.player = player;
        this.uuid = player.getUniqueId();
        init();
    }

    /**
     * Method ran by all constructors
     */
    private void init(){
        this.properties = new PlayerProperties();
    }
    public PlayerProperties getProperties(){
        return properties;
    }
    protected void setProperties(PlayerProperties properties){
        this.properties = properties;
    }
}
