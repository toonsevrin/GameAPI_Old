package com.exorath.game.lib.hud.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by TOON on 8/12/2015.
 */
public class ScoreboardBase {

    private org.bukkit.scoreboard.Scoreboard scoreboard;//The Bukkit scoreboard
    private Objective objective;//The Bukkit objective
    private BiMap<String, SpigboardEntry> entries;//All the entries (Double backed HashMap with 16 entries, both key and values can be null)

    private int teamId;

    public ScoreboardBase(String title) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("spigobjective", "dummy");//Register a new dummy objective on the scoreboard
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);//Make sure the objective displays on the sidebar
        setTitle(title);

        entries = HashBiMap.create();//Create a HashBiMap for the entries (Double backed HashMap with 16 entries, both key and values can be null)
        teamId = 1;
    }

    public org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setTitle(String title) {
        add();
        objective.setDisplayName(title);
    }

    public BiMap<String, SpigboardEntry> getEntries() {
        return HashBiMap.create(entries);
    }

    public SpigboardEntry getEntry(String key) {
        return entries.get(key);
    }

    public SpigboardEntry add(String name, int value) {
        return add((String) null, name, value, true);
    }

    public SpigboardEntry add(Enum<?> key, String name, int value) {
        return add(key.name(), name, value);
    }

    public SpigboardEntry add(String key, String name, int value) {
        return add(key, name, value, false);
    }

    public SpigboardEntry add(Enum<?> key, String name, int value, boolean overwrite) {
        return add(key.name(), name, value, overwrite);
    }

    public SpigboardEntry add(String key, String name, int value, boolean overwrite) {
        add();
        if (key == null && !contains(name))
            throw new IllegalArgumentException(
                    "Entry could not be found with the supplied name and no key was supplied");

        if (overwrite && contains(name)) {
            SpigboardEntry entry = getEntryByName(name);
            if (key != null && entries.get(key) != entry)
                throw new IllegalArgumentException(
                        "Supplied key references a different score than the one to be overwritten");

            entry.setValue(value);
            return entry;
        }

        if (entries.get(key) != null)
            throw new IllegalArgumentException("Score already exists with that key");

        int count = 0;
        String origName = name;
        if (!overwrite) {
            Map<Integer, String> created = create(name);
            for (Map.Entry<Integer, String> entry : created.entrySet()) {
                count = entry.getKey();
                name = entry.getValue();
            }
        }

        SpigboardEntry entry = new SpigboardEntry(key, this, value, origName, count);
        entry.update(name);
        entries.put(key, entry);
        return entry;
    }

    public void remove(String key) {
        remove(getEntry(key));
    }

    public void remove(SpigboardEntry entry) {
        add();
        if (entry.getSpigboard() != this)
            throw new IllegalArgumentException("Supplied entry does not belong to this Spigboard");

        String key = entries.inverse().get(entry);
        if (key != null)
            entries.remove(key);

        entry.remove();
    }

    private Map<Integer, String> create(String name) {
        // Bukkit.getLogger().info("Name: '" + name + "' (" + name.length() + ") contains? " + contains(name));
        int count = 0;
        while (contains(name)) {
            name = ChatColor.RESET + name;
            count++;
            // Bukkit.getLogger().info("Name: '" + name + "' (" + name.length() + ") contains? " + contains(name) + " (" + ++count + ")");
        }

        if (name.length() > 48)
            name = name.substring(0, 47);
        // Bukkit.getLogger().info("Name: '" + name + "' (" + name.length() + ") contains? " + contains(name) + " (trim)");

        if (contains(name))
            throw new IllegalArgumentException("Could not find a suitable replacement name for '" + name + "'");

        Map<Integer, String> created = new HashMap<Integer, String>();
        created.put(count, name);
        return created;
    }

    public int getTeamId() {
        return teamId++;
    }

    public SpigboardEntry getEntryByName(String name) {
        for (SpigboardEntry entry : entries.values())
            if (entry.getName().equals(name))
                return entry;

        return null;
    }

    public boolean contains(String name) {
        for (SpigboardEntry entry : entries.values())
            if (entry.getName().equals(name))
                return true;

        return false;
    }

    private boolean enabled = false;
    private GamePlayer gp;
    private Player player;

    public void add(GamePlayer gp) {
        this.gp = gp;
        add();

    }

    public void remove(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public void add() {
        if (player != null)
            return;
        if (gp == null)
            return;
        if (gp.getBukkitPlayer() == null)
            return;
        player = gp.getBukkitPlayer();
        enabled = true;
        player.setScoreboard(scoreboard);

    }
}
