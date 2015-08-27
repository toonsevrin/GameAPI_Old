package com.exorath.game.lib.hud.title;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TitleBase
{
    private static boolean initialized;
    private static Class<?> nmsChatSerializer;
    private static Class<?> nmsPacketTitle;
    private static Class<?> nmsTitleAction;
    private static Class<?> nmsPlayerConnection;
    private static Class<?> nmsEntityPlayer;
    private static Class<?> nmsChatBaseComponent;
    private static Class<?> ioNettyChannel;
    private static Method nmsSendPacket;
    private static Method nmsChatSerializerA;
    private static Method nmsNetworkGetVersion;
    private static Field nmsFieldPlayerConnection;
    private static Field nmsFieldNetworkManager;
    private static Field nmsFieldNetworkManagerI;
    //private static Field nmsFieldNetworkManagerM;
    private static double serverVersion = 1.7D;
    private static int VERSION = 47;

    public static void sendTitle(Player p, String title)
    {
        if (p == null || title == null) {
            throw new NullPointerException();
        }
        if (getVersion(p) < VERSION) {
            return;
        }
        try
        {
            Object handle = Reflection.getHandle(p);
            Object connection = nmsFieldPlayerConnection.get(handle);
            Object serialized = nmsChatSerializerA.invoke(null, new Object[] { title });
            Object packet;
            packet = nmsPacketTitle.getConstructor(new Class[] { nmsTitleAction, nmsChatBaseComponent }).newInstance(new Object[] { nmsTitleAction.getEnumConstants()[0], serialized });

            nmsSendPacket.invoke(connection, new Object[] { packet });
        }
        catch (Exception e)
        {
            System.err.println("[TitleManager] Error while sending title to Player " + p.getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void sendTitle(Player p, int fadeIn, int stay, int fadeOut, String title)
    {
        sendTimings(p, fadeIn, stay, fadeOut);
        sendTitle(p, title);
    }

    public static void sendSubTitle(Player p, String subtitle)
    {
        if (p == null || subtitle == null) {
            throw new NullPointerException();
        }
        if (getVersion(p) < VERSION) {
            return;
        }
        try
        {
            Object handle = Reflection.getHandle(p);
            Object connection = nmsFieldPlayerConnection.get(handle);
            Object serialized = nmsChatSerializerA.invoke(null, new Object[] { subtitle });
            Object packet;
            packet = nmsPacketTitle.getConstructor(new Class[] { nmsTitleAction, nmsChatBaseComponent }).newInstance(new Object[] { nmsTitleAction.getEnumConstants()[1], serialized });
            nmsSendPacket.invoke(connection, new Object[] { packet });
        }
        catch (Exception e)
        {
            System.err.println("[TitleManager] Error while sending subtitle to Player " + p.getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void sendSubTitle(Player p, int fadeIn, int stay, int fadeOut, String subtitle)
    {
        sendTimings(p, fadeIn, stay, fadeOut);
        sendSubTitle(p, subtitle);
    }

    public static void sendTimings(Player p, int fadeIn, int stay, int fadeOut)
    {
        if (p == null) {
            throw new NullPointerException();
        }
        if (getVersion(p) < VERSION) {
            return;
        }
        try
        {
            Object handle = Reflection.getHandle(p);
            Object connection = nmsFieldPlayerConnection.get(handle);
            Object packet;
            packet = nmsPacketTitle.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE }).newInstance(new Object[] { Integer.valueOf(fadeIn), Integer.valueOf(stay), Integer.valueOf(fadeOut) });
            nmsSendPacket.invoke(connection, new Object[] { packet });
        }
        catch (Exception e)
        {
            System.err.println("[TitleManager] Error while sending timings to Player " + p.getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void reset(Player p)
    {
        if (p == null) {
            throw new NullPointerException();
        }
        if (getVersion(p) < VERSION) {
            return;
        }
        try
        {
            Object handle = Reflection.getHandle(p);
            Object connection = nmsFieldPlayerConnection.get(handle);
            Object packet;
            packet = nmsPacketTitle.getConstructor(new Class[] { nmsTitleAction, nmsChatBaseComponent }).newInstance(new Object[] { nmsTitleAction.getEnumConstants()[4], null });
            nmsSendPacket.invoke(connection, new Object[] { packet });
        }
        catch (Exception e)
        {
            System.err.println("[TitleManager] Error while sending reset to Player " + p.getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static void clear(Player p)
    {
        if (p == null) {
            throw new NullPointerException();
        }
        if (getVersion(p) < VERSION) {
            return;
        }
        try
        {
            Object handle = Reflection.getHandle(p);
            Object connection = nmsFieldPlayerConnection.get(handle);
            Object packet;
            packet = nmsPacketTitle.getConstructor(new Class[] { nmsTitleAction, nmsChatBaseComponent }).newInstance(new Object[] { nmsTitleAction.getEnumConstants()[3], null });
            nmsSendPacket.invoke(connection, new Object[] { packet });
        }
        catch (Exception e)
        {
            System.err.println("[TitleManager] Error while sending clear to Player " + p.getName() + ": " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }

    public static int getVersion(Player p)
    {
        try
        {
            Object handle = Reflection.getHandle(p);
            Object connection = nmsFieldPlayerConnection.get(handle);
            Object network = nmsFieldNetworkManager.get(connection);
            Object channel;
            channel = nmsFieldNetworkManagerI.get(network);
            Object version = serverVersion == 1.7D ? nmsNetworkGetVersion.invoke(network, new Object[] { channel }) : Integer.valueOf(47);
            return ((Integer)version).intValue();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 180;
    }

    static
    {
        if (!initialized)
        {
            String ver = Reflection.getVersion();
            if (ver.contains("1_7")) {
                serverVersion = 1.7D;
            }
            if (ver.contains("1_8")) {
                serverVersion = 1.8D;
            }
            if (ver.contains("1_8_R2")) {
                serverVersion = 1.83D;
            }
            if (ver.contains("1_8_R3")) {
                serverVersion = 1.85D;
            }
            try
            {
                nmsChatBaseComponent = Reflection.getNMSClass("IChatBaseComponent");
                nmsChatSerializer = Reflection.getNMSClass(Reflection.getVersion().contains("1_7") || Reflection.getVersion().contains("1_8_R1") ? "ChatSerializer" : "IChatBaseComponent$ChatSerializer");
                if (Reflection.getVersion().contains("1_8"))
                {
                    nmsPacketTitle = Reflection.getNMSClass("PacketPlayOutTitle");
                    if (serverVersion >= 1.83D) {
                        nmsTitleAction = Reflection.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
                    } else {
                        nmsTitleAction = Reflection.getNMSClass("EnumTitleAction");
                    }
                }
                nmsPlayerConnection = Reflection.getNMSClass("PlayerConnection");
                nmsEntityPlayer = Reflection.getNMSClass("EntityPlayer");
                ioNettyChannel = serverVersion == 1.7D ? Class.forName("net.minecraft.util.io.netty.channel.Channel") : Class.forName("io.netty.channel.Channel");

                nmsFieldPlayerConnection = Reflection.getField(nmsEntityPlayer, "playerConnection");
                nmsFieldNetworkManager = Reflection.getField(nmsPlayerConnection, "networkManager");
                nmsFieldNetworkManagerI = Reflection.getField(nmsFieldNetworkManager.getType(), "i");
                //nmsFieldNetworkManagerM = Reflection.getField(nmsFieldNetworkManager.getType(), "m");

                nmsSendPacket = Reflection.getMethod(nmsPlayerConnection, "sendPacket", new Class[0]);
                nmsChatSerializerA = Reflection.getMethod(nmsChatSerializer, "a", new Class[] { String.class });
                nmsNetworkGetVersion = Reflection.getMethod(nmsFieldNetworkManager.getType(), "getVersion", new Class[] { ioNettyChannel });

                initialized = true;
            }
            catch (Exception e)
            {
                System.err.println("[TitleManager] Error while loading: " + e.getMessage());
                e.printStackTrace(System.err);
                Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("TitleManager"));
            }
        }
    }
}
