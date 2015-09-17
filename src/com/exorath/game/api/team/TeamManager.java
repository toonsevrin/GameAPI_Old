package com.exorath.game.api.team;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.exorath.game.lib.JoinLeave;
import org.bukkit.entity.Player;

import com.exorath.game.api.Game;
import com.exorath.game.api.Manager;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.maps.MapManager;
import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Maps;

/**
 * Created by too on 23/05/2015.
 * Manager instantiated in Game class. Manages the creation and implementation
 * of teams.
 */
public class TeamManager implements Manager, JoinLeave {

    private final Game game;

    public Map<TeamColor, Team> teams = Maps.newHashMap();

    public TeamManager(Game game) {
        this.game = game;
        addTeam(new DefaultTeam());
    }

    //** Join & Leave **//
    @Override
    public void join(GamePlayer player) {

    }

    @Override
    public void leave(GamePlayer player) {

    }
    //** Teams **//
    /* Adding & Removing */
    public void addTeam(Team team) {
        deleteDefaultTeam();
        teams.put(team.getTeamColor(), team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }

    /**
     * if there is one team and its a DefaultTeam remove it. DefaultTeam only
     * exists while no other teams are added.
     */
    private void deleteDefaultTeam() {
        for (Iterator<Team> it = teams.values().iterator(); it.hasNext();)
            if (it.next() instanceof DefaultTeam)
                it.remove();
    }

    /* Getting */
    public Game getGame() {
        return game;
    }
    public Collection<Team> getTeams() {
        return teams.values();
    }
    public Team getTeam() {
        return teams.size() == 1 ? teams.values().toArray(new Team[1])[0] : null;
    }
    public Team getTeam(GamePlayer gp) {
        Optional<Team>team = getTeams().stream().filter(t -> t.getPlayers().contains(gp.getUUID())).findAny();
        return team.isPresent() ? team.get() : null;
    }
    public Team getTeam(TeamColor color, boolean create) {
        if (create && !teams.containsKey(color))
            teams.put(color, new Team().setTeamColor(color));
        return teams.get(color);
    }
    public int getWeight(Team t) {
        return t.getPlayers().size() * t.getProperties().as(TeamProperty.PLAYER_WEIGHT, int.class);
    }

    /* Tests */
    public boolean hasMinPlayers() {
        for (Team team : getTeams())
            if (!team.hasEnoughPlayers())
                return false;
        return true;
    }
    public boolean hasPlayersPlaying(){
        for(Team team : getTeams())
            if(!team.getActivePlayers().isEmpty())
                return true;
        return false;
    }

    //** Ran onStart **//
    public void startGame() {
        GameMap map = getGame().getManager(MapManager.class).getCurrent();
        for (Team team : teams.values()) {
            int spawn = 0;
            for (GamePlayer player : team.getPlayers()) {
                Player pl = player.getBukkitPlayer();
                if (pl != null)
                    pl.teleport(map.getSpawn(team.getTeamColor(), spawn++).getBukkitLocation());
            }
        }
    }
}
