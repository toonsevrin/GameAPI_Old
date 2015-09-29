package me.nickrobson.lib.serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializableLocation implements Serializable {

    private Location loc;

    public SerializableLocation(World world, double x, double y, double z, float yaw, float pitch) {
        this(new Location(world, x, y, z, yaw, pitch));
    }

    public SerializableLocation(World world, double x, double y, double z) {
        this(new Location(world, x, y, z, 0, 0));
    }

    protected SerializableLocation(Location loc) {
        this.loc = loc;
    }

    public Location getBukkitLocation() {
        return loc;
    }

    @Override
    public String serialize() {
        String s = Serializer.SEPARATOR_INFO;
        return this.loc.getWorld().getName() + s + this.loc.getX() + s + this.loc.getY() + s + this.loc.getZ() + s
                + this.loc.getYaw() + s
                + this.loc.getPitch();
    }

    @Override
    public void deserialize(String s) {
        String[] data = s.split(";");
        World w = Bukkit.getWorld(data[0]);
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double z = Double.parseDouble(data[3]);
        float yaw = Float.parseFloat(data[4]);
        float pitch = Float.parseFloat(data[5]);
        this.loc.setWorld(w);
        this.loc.setX(x);
        this.loc.setY(y);
        this.loc.setZ(z);
        this.loc.setYaw(yaw);
        this.loc.setPitch(pitch);
    }

}
