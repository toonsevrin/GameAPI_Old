package com.exorath.game.api.gametype.minigame;

import com.exorath.game.api.Manager;
import com.exorath.game.api.gametype.minigame.kit.KitManager;
import com.exorath.game.api.hud.HUDManager;
import com.exorath.game.api.maps.MapManager;
import com.exorath.game.api.spectate.SpectateManager;
import com.exorath.game.api.team.TeamManager;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;
import com.exorath.game.api.GameListener;
import com.exorath.game.api.Property;
import com.exorath.game.api.player.GamePlayer;

import java.util.Arrays;

/**
 * @author Nick Robson
 */
public abstract class Minigame extends Game {

    public static final Property MIN_PLAYERS = Property.get("minplayers", "Minimal amount of players in team", 2);
    public static final Property MAX_DURATION = Property.get("maxduration",
            "The maximum duration of the game in ticks. 0 disables.", 0);
    public static final Property START_DELAY = Property.get("startdelay",
            "Waiting time after there are enough players before game starts", 200);

    public Minigame() {
        Manager[] managers = new Manager[]{new MinigameStateManager(this),new KitManager(this), new SpectateManager(this), new TeamManager(this)};
        Arrays.asList(managers).forEach(m -> addManager(m));

        addListener(new MinigameListener());
    }

    public boolean hasMinPlayers() {

        return getPlayers().getPlayerCount() >= getProperties().as(Minigame.MIN_PLAYERS, Integer.class);
    }
    public MinigameStateManager getStateManager() {
        return getManager(MinigameStateManager.class);
    }

    protected void spawnPlayers() {
        //TODO: Develop method
    }

    protected void reward() {
        //TODO: Develop method
        //This will call an abstract method that gets a reward package for each player.
    }

    protected void reset() {
        //TODO: Develop method
        //Reset map if enabled

        //Reset player inventories

        //Reset players health & hunger

        //Reset players potion effects

        //Teleport players to hub
    }

    private class MinigameListener implements GameListener {

        @Override
        public void onJoin(PlayerJoinEvent event, Game game, GamePlayer player) {
            GameAPI.printConsole("Player joined!");
            getStateManager().checkStart();
        }

        @Override
        public void onQuit(PlayerQuitEvent event, Game game, GamePlayer player) {
            GameAPI.printConsole("Player left!");
            getStateManager().checkStop();
        }
    }
}
