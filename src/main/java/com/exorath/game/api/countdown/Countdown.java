package com.exorath.game.api.countdown;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import com.exorath.game.GameAPI;
import com.exorath.game.api.Game;

/**
 * @author Nick Robson
 */
public class Countdown {

    private UUID gameUID;
    private Runnable done;
    private List<CountdownFrame> frames = new LinkedList<>();

    public Countdown(UUID gameUID, Runnable done) {
        this.done = done;
        this.gameUID = gameUID;
        new Task().runTaskTimer(GameAPI.getInstance(), 0, 1);
    }

    public Countdown(UUID gameUID) {
        this(gameUID, null);
    }

    public List<CountdownFrame> getFrames() {
        return frames;
    }

    private class Task extends BukkitRunnable {

        private int current = 0;

        private long started = 0;
        private long elapsed = 0;
        private long lastRun = 0;

        @Override
        public void run() {
            if (current == frames.size()) {
                if (done != null)
                    done.run();
                cancel();
                return;
            }
            Game game = GameAPI.getGame(gameUID);
            if (game == null) {
                cancel();
                return;
            }
            CountdownFrame frame = frames.get(current);
            if (elapsed - lastRun >= Math.min(frame.getDelay(), frame.getDuration())) {
                game.getOnlinePlayers().forEach(p -> frame.display(p));
                lastRun = elapsed;
            }
            if (elapsed == 0)
                game.getOnlinePlayers().forEach(p -> frame.start(p));
            if (elapsed - started >= frame.getDuration()) {
                game.getOnlinePlayers().forEach(p -> frame.end(p));
                if (current != frames.size() - 1) {
                    CountdownFrame nextFrame = frames.get(current + 1);
                    game.getOnlinePlayers().forEach(p -> nextFrame.start(p));
                    started = elapsed;
                }
                current++;
            }
            elapsed++;
        }

    }

}
