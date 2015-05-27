package com.demo;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameProperty;

/**
 * Created by too on 27/05/2015.
 * An example gamemode
 */
public class SurvivalGames extends Game{
    public SurvivalGames(){
        setupProperties();


    }
    private void setupProperties(){
        this.setName("Survival Games");
        this.setDescription("Tributes fight to the death in the dangerous arena.");
    }
}
