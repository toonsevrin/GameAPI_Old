package com.exorath.game.api.scoreboards;

import com.exorath.game.api.player.GamePlayer;
import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by TOON on 7/3/2015.
 */
public class Scoreboard {
    GamePlayer owner;

    public Scoreboard(GamePlayer owner) {
        this.owner = owner;
    }


    /* Bare scoreboard library - Created by ParaPenguin */
    private class BaseScoreboard {

        private org.bukkit.scoreboard.Scoreboard scoreboard;
        private Objective objective;
        private BiMap<String, SpigboardEntry> entries;

        private int teamId;

        public BaseScoreboard(String title) {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            this.objective = scoreboard.registerNewObjective("spigobjective", "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            setTitle(title);

            this.entries = HashBiMap.create();
            this.teamId = 1;
        }

        public org.bukkit.scoreboard.Scoreboard getScoreboard() {
            return scoreboard;
        }

        public Objective getObjective() {
            return objective;
        }

        public void setTitle(String title) {
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

        public SpigboardEntry add(Enum key, String name, int value) {
            return add(key.name(), name, value);
        }

        public SpigboardEntry add(String key, String name, int value) {
            return add(key, name, value, false);
        }

        public SpigboardEntry add(Enum key, String name, int value, boolean overwrite) {
            return add(key.name(), name, value, overwrite);
        }

        public SpigboardEntry add(String key, String name, int value, boolean overwrite) {
            if (key == null && !contains(name)) {
                throw new IllegalArgumentException("Entry could not be found with the supplied name and no key was supplied");
            }

            if (overwrite && contains(name)) {
                SpigboardEntry entry = getEntryByName(name);
                if (key != null && entries.get(key) != entry) {
                    throw new IllegalArgumentException("Supplied key references a different score than the one to be overwritten");
                }

                entry.setValue(value);
                return entry;
            }

            if (entries.get(key) != null) {
                throw new IllegalArgumentException("Score already exists with that key");
            }

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
            if (entry.getSpigboard() != this) {
                throw new IllegalArgumentException("Supplied entry does not belong to this Spigboard");
            }

            String key = entries.inverse().get(entry);
            if (key != null) {
                entries.remove(key);
            }

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

            if (name.length() > 48) {
                name = name.substring(0, 47);
                // Bukkit.getLogger().info("Name: '" + name + "' (" + name.length() + ") contains? " + contains(name) + " (trim)");
            }

            if (contains(name)) {
                throw new IllegalArgumentException("Could not find a suitable replacement name for '" + name + "'");
            }

            Map<Integer, String> created = new HashMap<Integer, String>();
            created.put(count, name);
            return created;
        }

        public int getTeamId() {
            return teamId++;
        }

        public SpigboardEntry getEntryByName(String name) {
            for (SpigboardEntry entry : entries.values()) {
                if (entry.getName().equals(name)) {
                    return entry;
                }
            }

            return null;
        }

        public boolean contains(String name) {
            for (SpigboardEntry entry : entries.values()) {
                if (entry.getName().equals(name)) {
                    return true;
                }
            }

            return false;
        }

        public void add(Player player) {
            player.setScoreboard(scoreboard);
        }
    }

    private class SpigboardEntry {
        private String key;
        private BaseScoreboard spigboard;
        private String name;
        private org.bukkit.scoreboard.Team team;
        private Score score;
        private int value;

        private String origName;
        private int count;

        public SpigboardEntry(String key, BaseScoreboard spigboard, int value) {
            this.key = key;
            this.spigboard = spigboard;
            this.value = value;
            this.count = 0;
        }

        public SpigboardEntry(String key, BaseScoreboard spigboard, int value, String origName, int count) {
            this.key = key;
            this.spigboard = spigboard;
            this.value = value;
            this.origName = origName;
            this.count = count;
        }

        public String getKey() {
            return key;
        }

        public BaseScoreboard getSpigboard() {
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
            if (!score.isScoreSet()) {
                score.setScore(-1);
            }

            score.setScore(value);
        }

        public void update(String newName) {
            int value = getValue();
            if (origName != null && newName.equals(origName)) {
                // String oldName = newName;
                for (int i = 0; i < count; i++) {
                    newName = ChatColor.RESET + newName;
                }

                // Bukkit.getLogger().info("Changed '" + oldName + "' (" + oldName.length() + ") into '" + newName + "' (" + newName.length() + ")");
            } else if (newName.equals(name)) {
                // Bukkit.getLogger().info("Not updating '" + newName + "' because it matches previous name");
                return;
            }

            create(newName);
            setValue(value);
        }

        void remove() {
            if (score != null) {
                score.getScoreboard().resetScores(score.getEntry());
            }

            if (team != null) {
                team.unregister();
            }
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

            // Credit to RainoBoy97 for this section here.
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
}
