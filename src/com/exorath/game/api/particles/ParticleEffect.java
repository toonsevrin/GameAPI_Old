package com.exorath.game.api.particles;

import com.exorath.game.GameAPI;
import com.exorath.game.api.particles.particleLocs.ParticleLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 * Created by Toon Sevrin on 8/28/2015.
 */
public abstract class ParticleEffect implements Runnable{
    private ParticleLocation baseLocation;

    private int frames;
    private int calls;
    private int interval;


    private int tick = 0;
    private int frame = 0;

    public ParticleEffect(ParticleLocation baseLocation, int frames, int calls, int interval){
        this.baseLocation = baseLocation;

        this.frames = frames;
        this.calls = calls;
        this.interval = interval;
    }

    public abstract Location getLocation(Location base, float frame, float call);

    // This is ran tickly
    @Override
    public void run(){
        if(!isReady())
            return;
        frame++;

        Location baseLoc = baseLocation.getBaseLocation();
        if(baseLoc == null)
            return;
        for(int call = 0; call < calls; call++)
            getLocation(baseLoc,frame, call);

        if(frame >= frames)
            return;
        Bukkit.getScheduler().runTaskLater(GameAPI.getInstance(), this, interval);
    }
    public boolean isReady(){
        tick++;
        if(tick == interval) {
            tick = 0;
            return true;
        }
        return false;
    }
    public int getInterval(){
        return interval;
    }

    public int getCalls() {
        return calls;
    }

    public int getFrames() {
        return frames;
    }
    public int getTotalTicks(){
        return interval * frames;
    }
}
