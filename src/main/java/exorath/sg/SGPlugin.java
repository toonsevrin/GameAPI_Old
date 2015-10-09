package exorath.sg;

import org.bukkit.plugin.java.JavaPlugin;

import com.exorath.game.api.Game;
import com.exorath.game.api.GameProvider;

/**
 * @author Nick Robson
 */
public class SGPlugin extends JavaPlugin implements GameProvider {

    @Override
    public Game create() {
        return new SurvivalGames();
    }

}
