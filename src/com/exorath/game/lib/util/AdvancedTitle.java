package com.exorath.game.lib.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by too on 24/05/2015.
 */
public class AdvancedTitle {
    
    public static enum JSONParam {
        /**
         * Controls whether the text is bold or not. Default false
         */
        BOLD,
        /**
         * Controls whether the text is italic or not. Default false
         */
        ITALIC,
        /**
         * Controls whether the text is underlined or not. Default false
         */
        UNDERLINED,
        /**
         * Controls whether the text is striked out or not. Default false
         */
        STRIKETHROUGH,
        /**
         * Controls whether the text is randomised constantly or not. Default false
         */
        OBFUSCATED;
    }
    
    /**
     * A util to create JSON messages.
     *
     * @author gpotter2
     */
    public static class JSONPart {
        ChatColor color;
        String string;
        boolean bold = false;
        boolean italic = false;
        boolean underlined = false;
        boolean strikethrough = false;
        boolean obfuscated = false;
        
        /**
         * A util to create a JSON part message, with a text and color.
         *
         * @author gpotter2
         */
        public JSONPart( String string, ChatColor color ) {
            if ( string == null ) {
                new NullPointerException( "The string cannot be null !" ).printStackTrace();
                return;
            }
            this.string = string.replaceAll( "'", "" ).replaceAll( '"' + "", "" );
            if ( color != null ) {
                this.color = color;
            } else {
                color = ChatColor.WHITE;
            }
        }
        
        public String getString() {
            return string;
        }
        
        public ChatColor getColor() {
            return color;
        }
        
        public String getJSONPart() {
            return "{text:'" + string + "',color:'" + color.name().toLowerCase() + "',bold:" + bold + ",italic:" + italic + ",underlined:"
                    + underlined
                    + ",strikethrough:" + strikethrough + ",obfuscated:" + obfuscated + "}";
        }
        
        public String __INVALID__getJSONPartExtra() {
            return "{text:'" + string + "',color:'" + color.name().toLowerCase() + "',bold:" + bold + ",italic:" + italic + ",underlined:"
                    + underlined
                    + ",strikethrough:" + strikethrough + ",obfuscated:" + obfuscated + ",extra:[";
        }
        
        public boolean isValid() {
            return ( string != null && color != null );
        }
        
        public JSONPart setParam( JSONParam... params ) {
            for ( JSONParam param : params ) {
                if ( param == JSONParam.BOLD ) {
                    bold = true;
                } else if ( param == JSONParam.ITALIC ) {
                    italic = true;
                } else if ( param == JSONParam.OBFUSCATED ) {
                    obfuscated = true;
                } else if ( param == JSONParam.STRIKETHROUGH ) {
                    strikethrough = true;
                } else if ( param == JSONParam.UNDERLINED ) {
                    underlined = true;
                }
            }
            return this;
        }
    }
    
    public static String JSONString( List<JSONPart> list ) {
        if ( list == null ) {
            new NullPointerException( "The list cannot be null !" ).printStackTrace();
            return null;
        }
        if ( list.size() < 1 ) {
            new IndexOutOfBoundsException( "The must contains at least 1 element !" ).printStackTrace();
            return null;
        }
        if ( list.size() > 1 ) {
            String result = "";
            boolean first_done = false;
            for ( int i = 0; i < list.size(); i++ ) {
                JSONPart json_part = list.get( i );
                if ( !first_done ) {
                    result = json_part.__INVALID__getJSONPartExtra();
                    first_done = true;
                } else {
                    if ( list.size() >= ( i + 2 ) ) {
                        result = result + json_part.__INVALID__getJSONPartExtra();
                    } else {
                        result = result + json_part.getJSONPart();
                        for ( int end = 0; end < i; end++ ) {
                            result = result + "]}";
                        }
                        return result;
                    }
                }
            }
        } else {
            return list.get( 0 ).getJSONPart();
        }
        return null;
    }
    
    /**
     * Send a subtitle to a player during a specified time.
     *
     * @author gpotter2
     * @param player
     *            The player to sent the title to
     * @param JSONsubtitle
     *            The subtitle to set
     * @param fadeIn
     *            The time during the title will appear (in ticks)
     * @param stay
     *            The time during the title will stay (in ticks)
     * @param fadeOut
     *            The time during the title will disappear (in ticks)
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     */
    public static void sendSubTitle( Player player, String JSONsubtitle, Integer fadeIn, Integer stay, Integer fadeOut )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchFieldException,
            SecurityException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        if ( player != null && JSONsubtitle != null ) {
            sendTitle( player, null, JSONsubtitle, fadeIn, stay, fadeOut );
        } else {
            new NullPointerException( "The vars: 'player' and 'JSONsubtitle' musn't be null !" ).printStackTrace();
        }
    }
    
    /**
     * Send a title to a player during a specified time.
     *
     * @author gpotter2
     * @param player
     *            The player to sent the title to
     * @param JSONtitle
     *            The title to set
     * @param fadeIn
     *            The time during the title will appear (in ticks)
     * @param stay
     *            The time during the title will stay (in ticks)
     * @param fadeOut
     *            The time during the title will disappear (in ticks)
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     */
    public static void sendTitle( Player player, String JSONtitle, Integer fadeIn, Integer stay, Integer fadeOut )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchFieldException,
            SecurityException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        if ( player != null && JSONtitle != null ) {
            sendTitle( player, JSONtitle, null, fadeIn, stay, fadeOut );
        } else {
            new NullPointerException( "The vars: 'player' and 'JSONtitle' musn't be null !" ).printStackTrace();
        }
    }
    
    /**
     * Send a title and a subtitle to a player during a specified time.
     *
     * @author gpotter2
     * @param player
     *            The player to sent the title to
     * @param JSONtitle
     *            The title to set
     * @param JSONsubtitle
     *            The subtitle to set
     * @param fadeIn
     *            The time during the title will appear (in ticks)
     * @param stay
     *            The time during the title will stay (in ticks)
     * @param fadeOut
     *            The time during the title will disappear (in ticks)
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     */
    public static void sendTitleAndSubTitle( Player player, String JSONtitle, String JSONsubtitle, Integer fadeIn, Integer stay, Integer fadeOut )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchFieldException,
            SecurityException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        if ( player != null && JSONtitle != null && JSONsubtitle != null ) {
            sendTitle( player, JSONtitle, JSONsubtitle, fadeIn, stay, fadeOut );
        } else {
            new NullPointerException( "The vars: 'player', 'JSONtitle' and 'JSONsubtitle' musn't be null !" ).printStackTrace();
        }
    }
    
    private static void sendTitle( Player player, String JSONtitle, String JSONsubtitle, Integer fadeIn, Integer stay, Integer fadeOut )
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchFieldException,
            SecurityException, ClassNotFoundException, NoSuchMethodException, InstantiationException {
        
        Field playerConnection = ReflectionUtils.getField( "EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection" );
        Constructor<?> packetConstructor = ReflectionUtils.getConstructor( ReflectionUtils.PackageType.MINECRAFT_SERVER
                .getClass( "PacketPlayOutTitle" ) );
        Method getIChatBaseComponent = ReflectionUtils.getMethod( "ChatSerializer", ReflectionUtils.PackageType.MINECRAFT_SERVER, "a", String.class );
        Method getHandle = ReflectionUtils.getMethod( "CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle" );
        Method sendPacket = ReflectionUtils.getMethod( playerConnection.getType(), "sendPacket",
                ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass( "Packet" ) );
        
        Class<?> enum_titleaction = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass( "EnumTitleAction" );
        Class<?> IChatBaseComponent_class = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass( "IChatBaseComponent" );
        
        Object JSONsubtitle_component = null;
        Object JSONtitle_component = null;
        if ( JSONtitle != null ) {
            JSONtitle_component = getIChatBaseComponent.invoke( IChatBaseComponent_class, JSONtitle );
        } else {
            JSONtitle_component = getIChatBaseComponent.invoke( IChatBaseComponent_class, "{text:''}" );
        }
        if ( JSONsubtitle != null ) {
            JSONsubtitle_component = getIChatBaseComponent.invoke( IChatBaseComponent_class, JSONsubtitle );
        }
        
        sendPacket.invoke( playerConnection.get( getHandle.invoke( player ) ),
                instancePacket( packetConstructor, enum_titleaction.getEnumConstants()[ 2 ], null, fadeIn, stay, fadeOut ) );
        sendPacket.invoke( playerConnection.get( getHandle.invoke( player ) ),
                instancePacket( packetConstructor, enum_titleaction.getEnumConstants()[ 0 ], JSONtitle_component, -1, -1, -1 ) );
        if ( JSONsubtitle != null ) {
            sendPacket.invoke( playerConnection.get( getHandle.invoke( player ) ),
                    instancePacket( packetConstructor, enum_titleaction.getEnumConstants()[ 1 ], JSONsubtitle_component, -1, -1, -1 ) );
        }
    }
    
    private static Object instancePacket( Constructor<?> packetConstructor, Object a, Object b, Object c, Object d, Object e )
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchFieldException, SecurityException {
        Object packet = null;
        packet = packetConstructor.newInstance();
        
        ReflectionUtils.setValue( packet, true, "a", a );
        ReflectionUtils.setValue( packet, true, "b", b );
        ReflectionUtils.setValue( packet, true, "c", c );
        ReflectionUtils.setValue( packet, true, "d", d );
        ReflectionUtils.setValue( packet, true, "e", e );
        return packet;
    }
}
