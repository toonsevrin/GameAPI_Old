package com.exorath.game.api.team;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Maps;
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
    private final Game game;

    private Set<Team> teams = new HashSet<>();
    public static Map<TeamColor, Team> teamColors = Maps.newHashMap();
    private boolean defaultTeam = true;

    public TeamManager(Game game) {
        this.game = game;
        this.teams.add(new DefaultTeam());
    }

    public Game getGame() {
        return this.game;
    }
    //** Teams **//
    /* Adding & Removing */
    public void addTeam(Team team) {
        this.deleteDefaultTeam();
        this.teams.add(team);
    }
    public void removeTeam(Team team) {
        teams.remove(team);
    }
    /**
     * if there is one team and its a DefaultTeam remove it. DefaultTeam only exists while no other teams are added.
     */
    private void deleteDefaultTeam() {
        if (!this.defaultTeam)
            return;
        this.teams.clear();
        this.defaultTeam = false;
    }
    /* Getting */
    public Set<Team> getTeams() {
        return this.teams;
    }
    public Team getTeam() {
        return teams.size() == 1 ? teams.toArray(new Team[1])[0] : null;
    }
    public Team getTeam(GamePlayer gp) {
        Optional<Team> team = getTeams().stream().filter(t -> t.getPlayers().contains(gp.getUUID())).findAny();
        return team.isPresent() ? team.get() : null;
    }
    public static Team getTeam(TeamColor color, boolean create) {
        if (create && !teamColors.containsKey(color)) {
            teamColors.put(color, new Team().setTeamColor(color));
        }
        return teamColors.get(color);
    }


        private Integer getWeight(Team t) {
        return t.getPlayers().size() * t.getProperties().as(TeamProperty.PLAYER_WEIGHT, int.class);
    }
    /* Tests */
    public boolean hasMinPlayers(){
        for(Team team : getTeams()){
            if(!team.hasEnoughPlayers())
                return false;
        }
        return true;
    }

    //** Ran onStart **//
    public void startGame() {
        GameMap map = this.getGame().getManager(MapManager.class).getCurrent();
        for (Team team : this. teams) {
            int spawn = 0;
            for (GamePlayer player : team.getPlayers()) {
                Player pl = player.getBukkitPlayer();
                if (pl != null)
                    pl.teleport(map.getSpawn(team, spawn++).getBukkitLocation());
            }
        }
    }
}
