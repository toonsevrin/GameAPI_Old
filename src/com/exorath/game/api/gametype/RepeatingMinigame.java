package com.exorath.game.api.gametype;

import com.exorath.game.api.Property;

/**
 * Created by too on 27/05/2015.
 * A minigame that keeps repeating when finished
 */
public class RepeatingMinigame extends Minigame {
    
    public static final Property REPEAT_DELAY = new Property( "repeating.delay", "The time between when a game ends and starts again", 45 );
    
}
