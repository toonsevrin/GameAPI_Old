package com.exorath.game.api.database;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by toon on 31/05/2015. Based on Nicks sql Library
 */
public class SQLManager {
    private String host;
    private int port;
    private String database;
    private String user;
    private String password;
    public SQLManager(String host, int port, String database, String user, String password){
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }
    /**
     * Get an SQLTable, formatted as pluginName_name
     * @param plugin plugin which table is used by.
     * @param tableName name of table.
     * @return Existing or new SQLTable with the formatted name.
     */
    public SQLTable getTable(JavaPlugin plugin, String tableName){
        return getTable(plugin.getName(), tableName);
    }

    public SQLTable getTable(String pluginName, String tableName){
        return null;
    }
}
