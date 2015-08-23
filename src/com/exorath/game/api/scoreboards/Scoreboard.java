package com.exorath.game.api.scoreboards;

import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.api.scoreboards.displayeffects.DisplayEffect;
import com.exorath.game.api.scoreboards.displayeffects.NoneEffect;
import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.*;

/**
 * Created by Toon Sevrin on 7/3/2015.
 * This is an object oriented way of handling the sidebar scoreboard
 * TODO: Still a lot of stuff
 */
public class Scoreboard {
    private GamePlayer owner; //The owner of this scoreboard
    private BiMap<String, ScoreboardLine> lines = HashBiMap.create(); //All registered lines for this scoreboard, note that only the highest priority ones display!


    public Scoreboard(GamePlayer owner) {
        this.owner = owner;
    }

    /**
     * Add a new line to the lines
     */
    public ScoreboardLine addLine(String value){
        return addLine(null, value, 0, new NoneEffect(), false);
    }
    public ScoreboardLine addLine(String value, int priority){
        return addLine(null, value, priority, new NoneEffect(), false);
    }
    public ScoreboardLine addLine(String value, int priority, DisplayEffect displayEffect){
        return addLine(null, value, priority, displayEffect, true);
    }

    public ScoreboardLine addLine(String value, int priority, DisplayEffect displayEffect, boolean visible){
        return addLine(null, value, priority, displayEffect, visible);
    }
    public ScoreboardLine addLine(String key, String value){
        return addLine(key, value, 0, new NoneEffect(), true);
    }
    public ScoreboardLine addLine(String key, String value, int priority){
        return addLine(key, value, priority, new NoneEffect(), true);
    }
    public ScoreboardLine addLine(String key, String value, int priority, DisplayEffect displayEffect){
        return addLine(key, value, priority, displayEffect, true);
    }
    public ScoreboardLine addLine(String key, String value, int priority, DisplayEffect displayEffect, boolean visible){
        if(key == null) key = UUID.randomUUID().toString();
        if(lines.containsKey(key)) return lines.get(key);
        ScoreboardLine line = new ScoreboardLine(this, key, value, priority, displayEffect, visible);
        return line;
    }
    public ScoreboardLine getLine(String key){
        return lines.getOrDefault(key, null);
    }
    public void deleteLine(ScoreboardLine line){
        if(lines.inverse().containsKey(line))
        lines.inverse().remove(line);
        //TODO: Delete the visual line
    }
    /**
     * Add this line to the updated
     * @param line Line that should be updated
     */
    protected void addUpdated(ScoreboardLine line){
        //TODO: Make sure this line updates
    }

    /**
     * Returns a sorted list of all visible lines (up to ~16)
     * @return
     */
    private List<ScoreboardLine> getVisibleLines(){
        TreeMap<Integer, List<ScoreboardLine>> sortedLines = new TreeMap<>();
        for(ScoreboardLine line : lines.values()){
            List<ScoreboardLine> lines = sortedLines.get(line.getPriority());
            if(lines == null)
                lines = new ArrayList<>();
            lines.add(line);
            sortedLines.put(line.getPriority(), lines);
        }
        List<ScoreboardLine> lines = new ArrayList<>();
        loop:
        for(int priority : sortedLines.descendingMap().keySet()){
            for(ScoreboardLine line : sortedLines.get(priority)){
                if(lines.size() >= 16){
                    break loop;
                }
                lines.add(line);
            }
        }
        return lines;
    }
    private class UpdateTask extends BukkitRunnable {
        public UpdateTask(){

        }
        @Override
        public void run(){
            updateLines();
            updateScoreboard();
        }
        private void updateLines(){

        }
        private void updateScoreboard(){

        }
    }










    /**
     * Bare scoreboard library - Created by ParaPenguin
     * This is the connection to the BukkitAPI with longer lines (Team prefix & suffix) and no flickering (entries)
     * https://github.com/ParaPenguin/Spigboard
     */
    private class BaseScoreboard {
        private org.bukkit.scoreboard.Scoreboard scoreboard; //The Bukkit scoreboard
        private Objective objective; //The Bukkit objective
        private BiMap<String, SpigboardEntry> entries; //All the entries (Double backed HashMap with 16 entries, both key and values can be null)

        private int teamId;

        public BaseScoreboard(String title) {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            this.objective = scoreboard.registerNewObjective("spigobjective", "dummy"); //Register a new dummy objective on the scoreboard
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR); //Make sure the objective displays on the sidebar
            setTitle(title);

            this.entries = HashBiMap.create(); //Create a HashBiMap for the entries (Double backed HashMap with 16 entries, both key and values can be null)
            this.teamId = 1; //TODO: Find out what this is
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
