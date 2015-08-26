package com.exorath.game.api.hud;

import java.util.PriorityQueue;

import com.exorath.game.api.player.GamePlayer;

/**
 * Created by TOON on 8/9/2015.
 */
public abstract class HUDDisplay extends HUDLocation {
    private int maxChars;
    private HUDText currentText;

    private PriorityQueue<HUDText> texts = new PriorityQueue<>();
    public HUDDisplay(GamePlayer player, int maxChars){
        super(player);
        this.maxChars = maxChars;
    }

    /**
     * @return The texts in the queue to display here
     */
    protected PriorityQueue<HUDText> getTexts() {
        return texts;
    }

    /**
     * Adds the text to the current queue if there is any, displays it if it has the highest
     * priority
     * 
     * @param text
     *            HUDText to display/add to queue
     */
    public void addText( HUDText text ) {
        if ( !active ) {
            return;
        }
        if ( getTexts().size() == 0 ) {//There is no text displaying atm
            displayText( text );
            currentText = text;
        } else if ( text.getPriority().getPriority() > getTexts().peek().getPriority().getPriority() ) {
            //The added text has a higher priority then the current highest one
            getTexts().poll();
            displayText( text );
            currentText = text;
        }
        text.setLocation( this );
    }

    /**
<<<<<<< HEAD
     * Removes
     * 
     * @param text
     *            HUDText to remove from queue (and from displaying)
=======
     * Removes texts from the display queue/display
     * @param text HUDText to remove from queue (and from displaying)
>>>>>>> master
     */
    public void removeText( HUDText text ) {
        if ( !active ) {
            return;
        }
        if ( !getTexts().contains( text ) ) {
            return;
        }
        if ( getTexts().peek() == text ) {//This text is currently being displayed
            removeCurrent();
            currentText = null;
        }
        getTexts().remove( text );
    }

    /**
     * This will be ran every tick
     */
    @Override
    public void run() {
        if ( !active ) {
            return;
        }
        if ( currentText == null ) {
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
    public void setActive( boolean active ) {
        this.active = active;
        if ( active ) {//If turned on, display the currentText
            if ( currentText != null ) {
                displayText( currentText );
            }
        } else {//If turned off, remove the current text and clear all texts
            if ( currentText != null ) {
                removeCurrent();
            }
        }
    }

    public void updated( HUDText text ) {
        if ( text == currentText ) {
            displayText( text );
        }
    }

    public HUDText getCurrentText() {
        return currentText;
    }

    //** Functions to be implemented by child **//

    /**
     * /**
     * Displays the text onto the display
     */
    public abstract void displayText( HUDText text );

    /**
     * Removes any text currently being displayed
     */
    public abstract void removeCurrent();
    /**
     * Disables/Enables the display
     * OBSOLETE: Will be integrated in removeText(HUDText text)
     */

}
