package com.exorath.game.api.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.util.Vector;

import com.exorath.game.api.Properties;
import com.exorath.game.api.npc.NPC;
import com.exorath.game.api.npc.SpawnedNPC;
import com.exorath.game.api.player.GamePlayer;

/**
 * Created by too on 23/05/2015.
 * This is the main Lobby class, it handles most of the lobby stuff (Area where
 * players get tp'ed to
 * between games).
 */
public class Lobby {

    private Properties properties = new Properties();

    public Lobby() {
        this.setupWorld();
    }

    private void setupWorld() {
        this.setWorld(Bukkit.createWorld(WorldCreator.name(properties.as(LobbyProperty.WORLD, String.class))));
        this.setSpawnLocation(-7.5f, 107, -0.5f);
        this.getSpawnLocation().setYaw(-90);
        this.getSpawnLocation().setPitch(-16);
    }

    public Properties getProperties() {
        return this.properties;
    }

    protected void setWorld(World world) {
        this.properties.set(LobbyProperty.WORLD, world.getName());
    }

    public World getWorld() {
        World world = Bukkit.getWorld(this.properties.as(LobbyProperty.WORLD, String.class));
        if (world == null)
            this.setupWorld();
        return world;
    }

    public Lobby enable() {
        this.properties.set(LobbyProperty.ENABLED, true);
        return this;
    }

    public boolean isEnabled() {
        return this.properties.as(LobbyProperty.ENABLED, boolean.class);
    }

    public void setSpawnLocation(float x, float y, float z) {
        this.properties.set(LobbyProperty.SPAWN, new Location(this.getWorld(), x, y, z));
    }

    public Location getSpawnLocation() {
        return this.properties.as(LobbyProperty.SPAWN, Location.class);
    }

    public void teleport(GamePlayer player) {
        if (player.isOnline())
            player.getBukkitPlayer().teleport(this.getSpawnLocation());
    }

    public void addNPC(NPC npc, Vector vector) {
        World w = this.getWorld();
        if (w != null)
            SpawnedNPC.spawn(npc, vector.toLocation(w));
    }

}
