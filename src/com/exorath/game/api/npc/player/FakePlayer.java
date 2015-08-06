package com.exorath.game.api.npc.player;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.exorath.game.api.nms.NMS;
import com.yoshigenius.lib.reflect.Reflection;

/**
 * @author Nick Robson
 */
public class FakePlayer {
    
    private String name, skin;
    
    public FakePlayer( String name, String skin ) {
        this.name = name;
        this.skin = skin;
        Validate.notNull( name, "Name cannot be null" );
        Validate.notNull( skin, "Skin cannot be null" );
    }
    
    /**
     * Spawns a new instance of this FakePlayer at the provided location.
     * <p>
     * <em>NOTE: This relies heavily on reflection. Expect it to be moved into
     *            NMSProviderImpl classes if in a future release some of this stuff breaks!</em>
     * </p>
     * 
     * @param loc
     *            The location.
     */
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    public Object spawnPlayer( Location loc ) {
        Object nms_DedicatedPlayerList = Reflection.getMethod( NMS.get().getBukkitServerClass(), "getHandle" ).invoke( Bukkit.getServer() );
        Object nms_WorldServer = NMS.get().getWorld( loc.getWorld() );
        Object nms_GameProfile = Reflection.getConstructor( NMS.get().getGameProfileClass(), UUID.class, String.class )
                .construct( UUID.randomUUID(), this.name );
                
        Object nms_DedicatedServer = Reflection.getMethod( nms_DedicatedPlayerList.getClass(), "getServer" )
                .invoke( nms_DedicatedPlayerList );
                
        Object nms_Player = Reflection.getConstructor( NMS.get().getPlayerClass(), NMS.get().getMinecraftServerClass(),
                NMS.get().getWorldServerClass(), NMS.get().getGameProfileClass(), NMS.get().getPlayerInteractManagerClass() )
                .construct( nms_DedicatedServer, nms_WorldServer, nms_GameProfile, NMS.get().createPlayerInteractManager( loc.getWorld() ) );
        Object nms_NetworkManager = NMS.get().createNetworkManager();
        Object nms_NetServerHandler = NMS.get().createNetServerHandler( nms_DedicatedServer, nms_NetworkManager, nms_Player );
        nms_NetServerHandler.getClass();// using it because I don't want to delete this bit
        
        Reflection.getMethod( NMS.get().getPlayerClass(), "spawnIn", NMS.get().getWorldClass() ).invoke( nms_Player, nms_WorldServer );
        Object nms_PlayerInteractManager = Reflection.getField( NMS.get().getPlayerClass(), "playerInteractManager" ).get( nms_Player );
        Reflection.getMethod( NMS.get().getPlayerInteractManagerClass(), "a", NMS.get().getWorldServerClass() )
                .invoke( nms_PlayerInteractManager, Reflection.getField( NMS.get().getPlayerClass(), "world" ).get( nms_Player ) );// pim.a(world);
        Object nms_WorldData = Reflection.getMethod( NMS.get().getWorldServerClass(), "getData" ).invoke( nms_WorldServer );
        Object nms_EnumGamemode = Reflection.getMethod( NMS.get().getClass( "WorldData" ), "getGameType" ).invoke( nms_WorldData );
        Reflection.getMethod( NMS.get().getPlayerInteractManagerClass(), "b", nms_EnumGamemode.getClass() )
                .invoke( nms_PlayerInteractManager, nms_EnumGamemode );// pim.b(gamemode);
        Reflection.getMethod( NMS.get().getPlayerClass(), "setPositionRotation", double.class, double.class, double.class, float.class,
                float.class ).invoke( nms_Player, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch() );
        Reflection.getMethod( NMS.get().getWorldClass(), "addEntity", NMS.get().getEntityClass() ).invoke( nms_WorldServer, nms_Player );
        List nms_ListEntityPlayer = (List) Reflection.getField( NMS.get().getClass( "PlayerList" ), "players" ).get( nms_DedicatedPlayerList );
        nms_ListEntityPlayer.add( nms_Player );
        Reflection.getMethod( NMS.get().getClass( "PlayerList" ), "a", NMS.get().getPlayerClass(), NMS.get().getWorldServerClass() )
                .invoke( nms_DedicatedPlayerList, nms_Player, nms_WorldServer );
                
        Object nms_ChunkProviderServer = Reflection.getField( NMS.get().getWorldServerClass(), "chunkProviderServer" ).get( nms_WorldServer );
        Reflection.getMethod( NMS.get().getClass( "ChunkProviderServer" ), "getOrCreateChunk", int.class, int.class )
                .invoke( nms_ChunkProviderServer, loc.getChunk().getX(), loc.getChunk().getZ() );
        return nms_Player;
        
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSkin() {
        return this.skin;
    }
    
}
