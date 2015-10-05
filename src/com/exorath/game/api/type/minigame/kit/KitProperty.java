package com.exorath.game.api.type.minigame.kit;

import com.exorath.game.api.Property;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default kit property keys.
 */
public class KitProperty {

    public static final Property NAME = Property.get("kit.name", "The name of this kit", Kit.DEFAULT_NAME);
    public static final Property ICON = Property.get("kit.icon", "The ItemStack that will be shown in inventories", Kit.DEFAULT_ICON);
    public static final Property DESCRIPTION = Property.get("kit.description", "The description of the kit.", Kit.DEFAULT_DESCRIPTION);
    public static final Property UNLOCK_REQUIREMENTS = Property.get("kit.unlockreq", "The kit might have some unlock requirements", null);

}
