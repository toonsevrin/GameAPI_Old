package com.exorath.game.api.hud;

import java.util.HashMap;
import java.util.PriorityQueue;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 8/9/2015.
 */
public abstract class HUDDisplay extends HUDLocation {

    private int maxChars;
    private HUDText currentText;

    private HashMap<String, HUDText> textsKeys = new HashMap<>();
    private PriorityQueue<HUDText> texts = new PriorityQueue<>();

    public HUDDisplay(GamePlayer player, int maxChars) {
        super(player);
        this.maxChars = maxChars;
    }

    public int getMaxChars() {
        return maxChars;
    }

    /**
     * @return The texts in the queue to display here
     */
    protected PriorityQueue<HUDText> getTexts() {
        return texts;
    }

    protected HashMap<String, HUDText> getTextsKeys() {
        return textsKeys;
    }

    /**
     * Adds the text to the current queue if there is any, displays it if it has
     * the highest
     * priority
     * 
     * @param text
     *            HUDText to display/add to queue
     */
    public void addText(String key, HUDText text) {
        if (!active)
            return;
        text.setLocation(this);
        if (getTexts().isEmpty()) {//No text displaying atm
            displayText(text);
            currentText = text;
        } else if (text.getPriority().getPriority() > getTexts().peek().getPriority().getPriority()) {
            //The added text has a higher priority then the current highest one
            displayText(text);
            currentText = text;
        }
        texts.add(text);
        textsKeys.put(key, text);
    }

    /**
     * @param text
     *            HUDText to remove from queue (and from displaying)
     */
    public void removeText(HUDText text) {
        if (!active)
            return;
        if (!texts.contains(text))
            return;
        if (texts.peek() == text) {//This text is currently being displayed
            removeCurrent();
            currentText = null;
        }
        texts.remove(text);
    }

    public void removeText(String key) {
        if (!active)
            return;
        if (!textsKeys.containsKey(key))
            return;
        HUDText text = textsKeys.get(key);
        if (!texts.contains(text))
            return;
        if (texts.peek().equals(text)) {//This text is currently being displayed
            removeCurrent();
            currentText = null;
        }
        texts.remove(text);
    }

    public boolean containsText(String key) {
        return getTextsKeys().containsKey(key);
    }

    public HUDText getText(String key) {
        return getTextsKeys().getOrDefault(key, null);
    }

    /**
     * This will be ran every tick
     */
    @Override
    public void run() {
        if (!active)
            return;
        if (currentText == null)
            return;
        if (!player.isOnline()) {
            cancel();
            return;
        }
        currentText.tick();
    }

    /**
     * Sets whether or not the display is active
     * 
     * @param active
     *            whether or not the display is active
     */
    @Override
    public void setActive(boolean active) {
        this.active = active;
        if (active) {//If turned on, display the currentText
            if (currentText != null)
                displayText(currentText);
        } else if (currentText != null)
            removeCurrent();
    }

    public void updated(HUDText text) {
        if (text == currentText)
            displayText(text);
    }

    public HUDText getCurrentText() {
        return currentText;
    }

    //** Functions to be implemented by child **//

    /**
     * /**
     * Displays the text onto the display
     */
    public abstract void displayText(HUDText text);

    /**
     * Removes any text currently being displayed
     */
    public abstract void removeCurrent();
    /**
     * Disables/Enables the display
     * OBSOLETE: Will be integrated in removeText(HUDText text)
     */

}
