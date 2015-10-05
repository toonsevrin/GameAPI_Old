package com.exorath.game.api.team;

import com.exorath.game.api.Property;
import com.exorath.game.api.player.PlayerProperty;

/**
 * @author toon
 * @author Nick Robson
 */
public class TeamProperty extends PlayerProperty {

    public static final Property NAME = Property.get("team.name", "The name of the team.", Team.DEFAULT_NAME);
    public static final Property MIN_SIZE = Property.get("team.players.min",
            "The minimum amount of players on the team to begin.", 0);
    public static final Property MAX_SIZE = Property.get("team.players.max", "How many players can join this team",
            Team.DEFAULT_MAX_PLAYER_AMOUNT);
    public static final Property PLAYER_WEIGHT = Property.get("team.playerweight",
            "How much this player weighs, used for balancing.", 1);
    public static final Property FRIENDLY_FIRE = Property.get("team.friendlyfire",
            "Whether or not pvp is allowed within the team", false);
    public static final Property COLOR = Property.get("team.color", "The team's color", TeamColor.RED);

}
