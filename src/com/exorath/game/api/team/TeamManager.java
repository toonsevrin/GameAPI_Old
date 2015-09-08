package com.exorath.game.api.team;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.bukkit.entity.Player;

import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.maps.MapManager;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by too on 23/05/2015.
 * Manager instantiated in Game class. Manages the creation and implementation
 * of teams.
 */
public class TeamManager implements Manager {

    private static TeamManager instance;
    private Game game;

    private Set<Team> teams = new HashSet<>();
    private boolean defaultTeam = true;

    public TeamManager(Game game) {
        instance = this;

        this.game = game;
        this.teams.add(new DefaultTeam());
    }

    public static TeamManager getInstance() {
        return instance;
    }

    public Game getGame() {
        return this.game;
    }

    /**
     * Add team to player teams.
     */
    public void addTeam(Team team) {
        this. deleteDefaultTeam();
        this.teams.add(team);
    }

    /**
     * Remove team from player teams.
     */
    public void removeTeam(Team team) {
        if (this. teams.contains(team))
            this.teams.remove(team);
    }

    /**
     * @return the teams that are added in the TeamManager.
     */
    public Set<Team> getTeams() {
        return this.teams;
    }

    /**
     * @return The only team if there is only one; otherwise null.
     */
    public Team getTeam() {
        return this.teams.size() == 1 ? this.teams.toArray(new Team[1])[0] : null;
    }

    public Team getTeam(GamePlayer gp) {
        Optional<Team>team = getTeams().stream().filter(t -> t.getPlayers().contains(gp.getUUID())).findAny();
        return team.isPresent() ? team.get() : null;
    }

    /**
     * if there is one team and its a DefaultTeam remove it. DefaultTeam only
     * exists while no other
     * teams are added.
     */
    private void deleteDefaultTeam() {
        if (!this.defaultTeam)
            return;
        this.teams.clear();
        this.defaultTeam = false;
    }

    public void startGame() {
        GameMap map = this.getGame().getManager(MapManager.class).getCurrent();
        for (Team team : this. teams) {
            int spawn = 0;
            for (GamePlayer player : team.getPlayers()) {
                Player pl = player.getBukkitPlayer();
                if (pl != null) {
                    pl.teleport(map.getSpawn(team, spawn++).getBukkitLocation());
                }
            }
        }
    }

    private Integer getWeight(Team t) {
        return t.getPlayers().size() * t.getProperties().as(TeamProperty.PLAYER_WEIGHT, int.class);
    }

    public Team findTeam(GamePlayer p) {
        Optional<Team> opt = getTeams().stream().min((t1, t2) -> getWeight(t1).compareTo(getWeight(t2)));
        if (opt.isPresent())
            return opt.get();
        return null;
    }

}
