package com.exorath.game.api.maps;

import com.exorath.game.api.Property;

/**
 * @author Nick Robson
 */
public class MapProperty {

    public static final Property NAME = Property.get("map.name", "The map's name", "Map");
    public static final Property VERSION = Property.get("map.version", "The map's version", "1.0");
    public static final Property CREATOR = Property.get("map.creator", "The map's creator", "Exorath");
    public static final Property DESCRIPTION = Property.get("map.description", "The map's description", "A game map");

}
