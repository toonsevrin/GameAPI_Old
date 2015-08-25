package example.simpleffa;

import com.exorath.game.api.Game;
import com.exorath.game.api.GamePlugin;

/**
 * Created by TOON on 8/23/2015.
 */
public class SimpleFFA extends GamePlugin {
    public SimpleFFA(){
        super(new FFAGame());
    }
}
