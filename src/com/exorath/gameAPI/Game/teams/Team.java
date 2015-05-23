package com.exorath.gameAPI.game.teams;

import com.exorath.gameAPI.lib.Properties;

/**
 * Created by too on 23/05/2015.
 * Base object for a Team.
 */
public class Team {
    private Properties properties;
    public Team(){
        properties = new Properties();
    }
    public Properties getProperties(){
        return properties;
    }
}
