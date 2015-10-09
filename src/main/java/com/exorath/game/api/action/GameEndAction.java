package com.exorath.game.api.action;

import org.bukkit.Bukkit;

import com.exorath.game.GameAPI;

/**
 * The GameEndAction is ran when the game ends, this is to add base behaviour to
 * this event
 * 
 * @author Nick Robson
 */
public abstract class GameEndAction {

    public abstract void end();

    public static class SendToServer extends GameEndAction {

        private String server;

        public SendToServer(String server) {
            this.server = server;
        }

        @Override
        public void end() {
            GameAPI.sendPlayersToServer(server, Bukkit.getOnlinePlayers());
        }
    }

}
