package com.exorath.game.api.maps;

import java.util.List;

import com.exorath.game.GameAPI;
import com.google.common.collect.Lists;

/**
 * @author Nick Robson
 */
public class Spawns {

    private int current = 0;

    private final List<Spawn> spawns = Lists.newArrayList();

    public void reset() {
        current = 0;
    }

    public Spawn getNextSpawn() {
        if (spawns.isEmpty())
            return null;
        return spawns.get(current == spawns.size() ? (current = 0) : current++);
    }

    public Spawn getSpawn(int index) {
        if (index < 0 || index >= spawns.size())
            return null;
        return spawns.get(index);
    }

    public void addSpawn(Spawn spawn) {
        spawns.add(spawn);
    }

    public void setSpawn(int index, Spawn spawn) {
        spawns.set(index, spawn);
    }

    public Spawn[] getSpawns() {
        return spawns.toArray(new Spawn[spawns.size()]);
    }

}
