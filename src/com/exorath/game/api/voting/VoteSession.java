package com.exorath.game.api.voting;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import com.exorath.game.api.player.GamePlayer;
import com.google.common.collect.Maps;

/**
 * @author Nick Robson
 */
public class VoteSession {

    final String title;
    final List<VoteOption> options;
    final Map<String, Vote> votes = Maps.newHashMap();// values are indices in options list.
    boolean revoteAllowed = false, open = false;

    public VoteSession(String title, List<VoteOption> options) {
        this.title = title;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public List<VoteOption> getOptions() {
        return options;
    }

    public boolean isRevoteAllowed() {
        return revoteAllowed;
    }

    public void setRevoteAllowed(boolean allowed) {
        revoteAllowed = allowed;
    }

    public Vote getVote(GamePlayer player) {
        return votes.get(player.getUUID().toString().intern());
    }

    public VoteResult vote(GamePlayer player, Vote vote) {
        if (!open)
            return VoteResult.VOTING_CLOSED;
        else if (options == null)
            return VoteResult.FAILURE;
        else if (vote.session != this)
            return VoteResult.FAILURE;
        else if (vote.option <= 0 || vote.option > options.size())
            return VoteResult.INVALID_OPTION;
        else if (!revoteAllowed && votes.containsKey(player.getUUID().toString().intern()))
            return VoteResult.ALREADY_VOTED;
        else {
            votes.put(player.getUUID().toString().intern(), vote);
            return VoteResult.SUCCESS;
        }
    }

    public void display(GamePlayer player) {
        if (options == null || options.isEmpty())
            return;
        player.sendMessage(ChatColor.GREEN + "===[ " + title + " ]===");
        for (int i = 0; i < options.size(); i++)
            player.sendMessage(ChatColor.YELLOW + "" + (i + 1) + ". " + options.get(i));
    }

    public void open() {
        if (open)
            return;
        open = true;
        votes.clear();
    }

    public void close() {
        if (!open)
            return;
        open = false;
    }

    public VoteOption getWinner() {
        if (open)
            return null;
        Map<Integer, Integer> numVotes = Maps.newHashMap();
        votes.entrySet().forEach(e -> numVotes.put(e.getValue().option,
                numVotes.getOrDefault(e.getValue().option, 0) + e.getValue().weight));

        int winner = -1;
        for (Entry<Integer, Integer> entry : numVotes.entrySet())
            if (winner == -1 || entry.getValue() > numVotes.get(winner))
                winner = entry.getKey();
        return options.get(winner);
    }

}
