package com.exorath.game.api.hud.locations.scoreboard;

import java.util.HashMap;
import java.util.PriorityQueue;

import org.bukkit.ChatColor;

import com.exorath.game.api.hud.HUDLocation;
import com.exorath.game.api.hud.HUDPriority;
import com.exorath.game.api.hud.HUDText;
import com.exorath.game.api.player.GamePlayer;
import com.exorath.game.lib.hud.scoreboard.ScoreboardBase;

/**
 * Created by TOON on 8/11/2015.
 * TODO: CREATE THIS CLASS
 */
public class Scoreboard extends HUDLocation {

    private HUDText title = new HUDText(ChatColor.BOLD + "Title", HUDPriority.GAME_API);
    private ScoreboardBase scoreboard;

    private HashMap<String, ScoreboardText> textsKeys = new HashMap<>();
    private PriorityQueue<ScoreboardText> texts = new PriorityQueue<>();

    public Scoreboard(GamePlayer player) {
        super(player);
        scoreboard = new ScoreboardBase(title.getDisplayText());
        scoreboard.add(player.getBukkitPlayer());
    }

    protected PriorityQueue<ScoreboardText> getTexts() {
        return texts;
    }

    protected HashMap<String, ScoreboardText> getTextsKeys() {
        return textsKeys;
    }

    public void addText(String key, ScoreboardText text) {
        if (!isActive())
            return;
        if (getTexts().contains(text))
            return;
        texts.add(text);
        textsKeys.put(key, text);

        text.setEntry(scoreboard.add(text.getDisplayText(), -1));
        priorityUpdated();
    }

    public void removeText(ScoreboardText text) {
        if (!isActive())
            return;
        if (!getTexts().contains(text))
            return;
        texts.remove(text);
        textsKeys.entrySet().stream().filter(s -> s.getValue().equals(text)).forEach(s -> textsKeys.remove(s));
        scoreboard.remove(text.getEntry());
    }

    public void removeText(String key) {
        if (!isActive())
            return;
        if (!textsKeys.containsKey(key))
            return;
        ScoreboardText text = textsKeys.get(key);
        if (!texts.contains(text))
            return;
        texts.remove();
        textsKeys.remove(key);
        scoreboard.remove(text.getEntry());
    }

    public boolean containsText(String key) {
        return textsKeys.containsKey(key);
    }

    public HUDText getText(String key) {
        return textsKeys.getOrDefault(key, null);
    }

    public void updated(ScoreboardText text) {//A text has updated on the scoreboard
        if (!isActive())
            return;
        if (text.equals(title)) {
            updateTitle();
            return;
        }
        if (!getTexts().contains(text))
            return;
        if (text.isTextUpdated()) //Text updated
            text.getEntry().update(text.getDisplayText());
        else if (text.isPriorityUpdated()) //Priority updated
            priorityUpdated();
    }

    /**
     * This method updates all entries their value to the new value
     */
    private void priorityUpdated() {
        int length = getVisibleTexts().length;
        for (int i = 0; i < length; i++)
            getVisibleTexts()[i].getEntry().setValue(length - 1 - i);
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
        if (active) //If turned on, display the currentText
            scoreboard.add(player.getBukkitPlayer());
        else//If turned off, hide all texts
            scoreboard.remove(player.getBukkitPlayer());
    }

    @Override
    public void run() {
        if (!active)
            return;
        title.tick();
        for (ScoreboardText text : getVisibleTexts())
            text.tick();
    }

    private ScoreboardText[] getVisibleTexts() {
        ScoreboardText[] texts = getTexts().toArray(new ScoreboardText[getTexts().size()]);
        if (getTexts().size() <= 16)
            return texts;
        ScoreboardText[] scoreboardTexts = new ScoreboardText[16];
        for (int i = 0; i < 16; i++)
            scoreboardTexts[i] = texts[i];
        return scoreboardTexts;
    }

    //** ScoreboardBase methods **//
    protected ScoreboardBase getScoreboard() {
        return scoreboard;
    }

    protected void setScoreboard(ScoreboardBase scoreboard) {
        this.scoreboard = scoreboard;
    }

    public HUDText getTitle() {
        return title;
    }

    private void updateTitle() {
        if (!isActive())
            return;
        scoreboard.setTitle(title.getDisplayText());
    }
}
