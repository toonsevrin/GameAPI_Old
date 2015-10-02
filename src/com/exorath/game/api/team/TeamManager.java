package com.exorath.game.api.team;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.exorath.game.api.Manager;
import com.exorath.game.api.maps.GameMap;
import com.exorath.game.api.maps.Spawns;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.type.minigame.Minigame;
import com.exorath.game.lib.JoinLeave;
import com.google.common.collect.Maps;

/**
 * Created by too on 23/05/2015.
 * Manager instantiated in Game class. Manages the creation and implementation
 * of teams.
 */
public class TeamManager implements Manager, JoinLeave {

    private final Minigame game;

    public Map<TeamColor, Team> teams = Maps.newHashMap();

    public TeamManager(Minigame game) {
        this.game = game;
        addTeam(new DefaultTeam());
    }

    //** Join & Leave **//
    @Override
    public void join(GamePlayer player) {
        if (addToDefault(player))
            player.sendMessage("You successfully joined " + getTeam(player).getTeamColor().toString());
        else
            player.sendMessage(ChatColor.RED + "All teams are full :(");
    }

    @Override
    public void leave(GamePlayer player) {
        Team team = getTeam(player);
        if (team == null)
            return;
        team.removePlayer(player);
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
    public Minigame getGame() {
        return game;
    }

    public Collection<Team> getTeams() {
        return teams.values();
    }

    public Team getTeam() {
        return teams.size() == 1 ? teams.values().toArray(new Team[1])[0] : null;
    }

    public Team getTeam(GamePlayer gp) {
        Optional<Team>team = getTeams().stream().filter(t -> t.getPlayers().contains(gp)).findAny();
        return team.isPresent() ? team.get() : null;
    }

    public Team getTeam(TeamColor color, boolean create) {
        if (create && !teams.containsKey(color))
            teams.put(color, new Team().setTeamColor(color));
        return teams.get(color);
    }

    public boolean addToDefault(GamePlayer player) {
        Team t = null;
        for (Team team : teams.values()) {
            if (team.getMaxTeamSize() == team.getPlayers().size()) //team is full
                continue;
            if (t == null || team.getTotalWeight() < t.getTotalWeight())
                t = team;
        }
        if (t == null)
            return false;

        t.addPlayer(player);
        return true;
    }

    /* Tests */
    public boolean hasMinPlayers() {
        for (Team team : getTeams())
            if (!team.hasEnoughPlayers())
                return false;
        return true;
    }

    public boolean hasPlayersPlaying() {
        for (Team team : getTeams())
            if (!team.getActivePlayers().isEmpty())
                return true;
        return false;
    }

    //** Ran onStart **//
    public void startGame() {
        GameMap map = getGame().getCurrent();
        for (Team team : teams.values()) {
            Spawns s = map.getSpawns(team.getTeamColor().name());
            s.reset();
            for (GamePlayer player : team.getPlayers()) {
                Player pl = player.getBukkitPlayer();
                if (pl != null)
                    pl.teleport(s.getNextSpawn().getLocation());
            }
        }
    }
    public void reset(){
        teams.values().forEach(t -> t.clear());
    }
}
