package com.exorath.game.api.maps;

import org.bukkit.Location;
import org.bukkit.World;

import com.exorath.game.api.team.TeamColor;
import com.yoshigenius.lib.serializable.SerializableLocation;
import com.yoshigenius.lib.serializable.Serializer;

public class GameSpawn extends SerializableLocation {

    private TeamColor team;

    public GameSpawn(Location loc) {
        this(null, loc);
    }

    public GameSpawn(TeamColor team, Location loc) {
        this(team, loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public GameSpawn(TeamColor team, World world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);
        this.team = team;
    }

    public GameSpawn(TeamColor team, World world, double x, double y, double z) {
        this(team, world, x, y, z, 0, 0);
    }

    public TeamColor getTeamColor() {
        return team;
    }

    @Override
    public String serialize() {
        return super.serialize() + Serializer.SEPARATOR_INFO + team.name().toLowerCase();
    }

    @Override
    public void deserialize(String s) {
        super.deserialize(s);
        String[] data = s.split(Serializer.SEPARATOR_INFO);
        team = data.length > 5 ? TeamColor.valueOf(data[6].toUpperCase()) : null;
    }

}
