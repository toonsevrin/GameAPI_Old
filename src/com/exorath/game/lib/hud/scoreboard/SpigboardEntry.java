package com.exorath.game.lib.hud.scoreboard;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;

import com.google.common.base.Splitter;

/**
 * Created by TOON on 8/12/2015.
 */
public class SpigboardEntry {

    private String key;
    private ScoreboardBase spigboard;
    private String name;
    private org.bukkit.scoreboard.Team team;
    private Score score;
    private int value;

    private String origName;
    private int count;

    public SpigboardEntry(String key, ScoreboardBase spigboard, int value) {
        this.key = key;
        this.spigboard = spigboard;
        this.value = value;
        count = 0;
    }

    public SpigboardEntry(String key, ScoreboardBase spigboard, int value, String origName, int count) {
        this.key = key;
        this.spigboard = spigboard;
        this.value = value;
        this.origName = origName;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public ScoreboardBase getSpigboard() {
        return spigboard;
    }

    public String getName() {
        return name;
    }

    public org.bukkit.scoreboard.Team getTeam() {
        return team;
    }

    public Score getScore() {
        return score;
    }

    public int getValue() {
        return score != null ? (value = score.getScore()) : value;
    }

    public void setValue(int value) {
        if (!score.isScoreSet())
            score.setScore(-1);
        score.setScore(value);
    }

    public void update(String newName) {
        int value = getValue();
        if (origName != null && newName.equals(origName))
            // String oldName = newName;
            for (int i = 0; i < count; i++)
                newName = ChatColor.RESET + newName;
        else if (newName.equals(name))
            // Bukkit.getLogger().info("Not updating '" + newName + "' because it matches previous name");
            return;

        create(newName);
        setValue(value);
    }

    void remove() {
        if (score != null)
            score.getScoreboard().resetScores(score.getEntry());

        if (team != null)
            team.unregister();
    }

    private void create(String name) {
        this.name = name;
        remove();

        if (name.length() <= 16) {
            int value = getValue();
            score = spigboard.getObjective().getScore(name);
            score.setScore(value);
            return;
        }

        team = spigboard.getScoreboard().registerNewTeam("spigboard-" + spigboard.getTeamId());
        Iterator<String> iterator = Splitter.fixedLength(16).split(name).iterator();
        if (name.length() > 16)
            team.setPrefix(iterator.next());
        String entry = iterator.next();
        score = spigboard.getObjective().getScore(entry);
        if (name.length() > 32)
            team.setSuffix(iterator.next());

        team.addEntry(entry);
    }
}
