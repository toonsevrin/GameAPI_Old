package com.exorath.game.api.maps;

import com.exorath.game.GameAPI;
import org.bukkit.World;

import java.io.File;

/**
 * Created by TOON on 6/27/2015.
 */
public class MapManager {
    private File folder;
    public MapManager(){
        folder = new File(GameAPI.getInstance().getFile(), "maps"); //Should be the folder maps under GameAPI
    }
}
