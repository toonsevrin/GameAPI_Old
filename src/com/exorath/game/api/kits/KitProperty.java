package com.exorath.game.api.kits;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.UnlockRequirements;
import org.bukkit.Material;

import java.util.List;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default kit property keys.
 */
public enum KitProperty {
    //primary
    //TODO: Create unlockable class
    NAME("name","The name of this kit", String.class),
    MATERIAL("material","The material that will be shown in inventories", Material.class),
    DESCRIPTION("description", "The description of the kit.", List.class),
    UNLOCK_REQUIREMENTS("unlockedwith","The kit might have some unlock requirements", UnlockRequirements.class);
    //Interaction

    private String key;
    private String description;
    private Class type;
    KitProperty(String key, String description, Class type){
        this.key = GameAPI.PREFIX + key;
        this.description = description;
        this.type = type;
    }
    public String getKey(){
        return key;
    }
    public String getDescription(){
        return description;
    }
    public Class getType(){
        return type;
    }
}
