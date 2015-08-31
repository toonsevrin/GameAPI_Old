package com.exorath.game.api.npc.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInChat;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.WorldServer;

public class FakePlayerNetServerHandler extends PlayerConnection {
    
    public FakePlayerNetServerHandler( MinecraftServer minecraftserver, NetworkManager inetworkmanager, EntityPlayer entityplayer ) {
        super( minecraftserver, inetworkmanager, entityplayer );
    }
    
    @Override
    public CraftPlayer getPlayer() {
        return this.player == null ? null : this.player.getBukkitEntity();
    }
    
    @Override
    public void disconnect( String s ) {
        WorldServer worldserver = this.player.u();
        
        worldserver.kill( this.player );
        worldserver.removeEntity( this.player );
        ( (CraftServer) Bukkit.getServer() ).getHandle().players.remove( this.player );
    }
    
    @Override
    public void a( double d0, double d1, double d2, float f, float f1 ) {}
    
    @Override
    public void teleport( Location dest ) {}
    
    @Override
    public void chat( String s, boolean async ) {}
    
    @Override
    public void a( PacketPlayInFlying packet10flying ) {}
    
    @Override
    public void a( PacketPlayInBlockDig packet14blockdig ) {}
    
    @Override
    public void a( PacketPlayInCloseWindow pkt ) {}
    
    @Override
    public void a( PacketPlayInBlockPlace packet15place ) {}
    
    @Override
    @SuppressWarnings( "rawtypes" )
    public void sendPacket( Packet packet ) {}
    
    @Override
    public void a( PacketPlayInHeldItemSlot packet16blockitemswitch ) {}
    
    @Override
    public void a( PacketPlayInChat packet3chat ) {}
    
    @Override
    public void a( PacketPlayInArmAnimation packet18armanimation ) {}
    
    @Override
    public void a( PacketPlayInEntityAction packet19entityaction ) {}
    
    @Override
    public void a( PacketPlayInUseEntity packet7useentity ) {}
    
    @Override
    public void a( PacketPlayInClientCommand packet205clientcommand ) {}
    
    @Override
    public void a( PacketPlayInWindowClick packet102windowclick ) {}
    
    @Override
    public void a( PacketPlayInSetCreativeSlot packet107setcreativeslot ) {}
    
    @Override
    public void a( PacketPlayInTransaction packet106transaction ) {}
    
    @Override
    public void a( PacketPlayInUpdateSign packet130updatesign ) {}
    
    @Override
    public void a( PacketPlayInKeepAlive packet0keepalive ) {}
    
    @Override
    public void a( PacketPlayInAbilities packet202abilities ) {}
    
    @Override
    public void a( PacketPlayInTabComplete packet203tabcomplete ) {}
    
    @Override
    public void a( PacketPlayInCustomPayload packet250custompayload ) {}
    
}
