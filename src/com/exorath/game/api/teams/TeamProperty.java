package com.exorath.game.api.teams;

import com.exorath.game.api.Property;
import com.exorath.game.api.players.PlayerProperty;
import org.bukkit.GameMode;

/**
 * Created by too on 23/05/2015.
 * This is an enum with all default team property keys.
 */
public class TeamProperty extends PlayerProperty {

    public static final Property
            NAME = Property.get("name", "The name of the team.", Team.DEFAULT_NAME),
            SIZE = Property.get("teamsize", "How many players can join this team", Team.DEFAULT_MAX_PLAYER_AMOUNT),
            PLAYER_WEIGHT = Property.get("playerweight", "How much this player weighs, used for balancing", 1),
            FRIENDLY_FIRE = Property.get("friendlyfire", "Whether or not pvp is allowed within the team", false);
}
