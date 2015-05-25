package com.exorath.game.lib.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.exorath.game.GameAPI;
import com.exorath.game.lib.jnbt.ByteArrayTag;
import com.exorath.game.lib.jnbt.CompoundTag;
import com.exorath.game.lib.jnbt.NBTInputStream;
import com.exorath.game.lib.jnbt.ShortTag;
import com.exorath.game.lib.jnbt.StringTag;
import com.exorath.game.lib.jnbt.Tag;

public class Schematic {
    
    private short[] blocks;
    private byte[] data;
    private short width;
    private short length;
    private short height;
    
    private Schematic( short[] blocks, byte[] data, short width, short length, short height ) {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }
    
    public short[] getBlocks() {
        return this.blocks;
    }
    
    public void setBlocks( short[] blocks ) {
        this.blocks = blocks;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public void setData( byte[] data ) {
        this.data = data;
    }
    
    public short getWidth() {
        return this.width;
    }
    
    public void setWidth( short width ) {
        this.width = width;
    }
    
    public short getLength() {
        return this.length;
    }
    
    public void setLength( short length ) {
        this.length = length;
    }
    
    public short getHeight() {
        return this.height;
    }
    
    public void setHeight( short height ) {
        this.height = height;
    }
    
    @Override
    public Schematic clone() {
        return new Schematic( this.blocks, this.data, this.width, this.length, this.height );
    }
    
    @SuppressWarnings( "deprecation" )
    public void paste( World world, Location loc ) {
        for ( int x = 0; x < this.width; ++x ) {
            for ( int y = 0; y < this.height; ++y ) {
                for ( int z = 0; z < this.length; ++z ) {
                    int index = y * this.width * this.length + z * this.width + x;
                    Block block = new Location( world, x + loc.getX(), y + loc.getY(), z + loc.getZ() ).getBlock();
                    block.setTypeIdAndData( this.blocks[ index ], this.data[ index ], true );
                }
            }
        }
    }
    
    @SuppressWarnings( "deprecation" )
    public void pasteWithInterval( final World world, final Location loc, final int interval ) {
        int loop = 0;
        for ( int x = 0; x < this.width; ++x ) {
            for ( int z = 0; z < this.length; ++z ) {
                for ( int y = 0; y < this.height; ++y ) {
                    loop++;
                    final int fX = x;
                    final int fY = y;
                    final int fZ = z;
                    Bukkit.getScheduler().scheduleSyncDelayedTask( GameAPI.getInstance(), ( ) -> {
                        int index = fY * Schematic.this.width * Schematic.this.length + fZ * Schematic.this.width + fX;
                        Block block = new Location( world, fX + loc.getX(), fY + loc.getY(), fZ + loc.getZ() ).getBlock();
                        block.setTypeIdAndData( Schematic.this.blocks[ index ], Schematic.this.data[ index ], true );
                        
                    }, interval * loop );
                    
                }
            }
        }
    }
    
    // Rotates once around the X-axis
    public void rotateX( boolean clockwise ) {
        this.rotate( 0, clockwise );
    }
    
    // Rotates a number of times clockwise around the X-axis
    // once would be 90 degrees, 2 would be 180, etc.
    public void rotateX( int num ) {
        while ( num-- > 0 ) {
            this.rotate( 0, true );
        }
    }
    
    // Rotates once around the Y-axis
    public void rotateY( boolean clockwise ) {
        this.rotate( 1, clockwise );
    }
    
    // Rotates a number of times clockwise around the Y-axis
    // once would be 90 degrees, 2 would be 180, etc.
    public void rotateY( int num ) {
        while ( num-- > 0 ) {
            this.rotate( 1, true );
        }
    }
    
    // Rotates once around the Z-axis
    public void rotateZ( boolean clockwise ) {
        this.rotate( 2, clockwise );
    }
    
    // Rotates a number of times clockwise around the Z-axis
    // once would be 90 degrees, 2 would be 180, etc.
    public void rotateZ( int num ) {
        while ( num-- > 0 ) {
            this.rotate( 2, true );
        }
    }
    
    // Rotates around an axis - 0=X, 1=Y, 2=Z
    public void rotate( int axis, boolean clockwise ) {
        
        // Initialize temp arrays
        short[] tempBlocks = new short[this.width * this.length * this.height];
        byte[] tempData = new byte[this.width * this.length * this.height];
        
        // Get the new dimensions
        short newWidth, newHeight, newLength;
        if ( axis == 0 ) {
            newWidth = this.width;
            newHeight = this.length;
            newLength = this.height;
        } else if ( axis == 1 ) {
            newWidth = this.length;
            newHeight = this.height;
            newLength = this.width;
        } else {
            newWidth = this.height;
            newHeight = this.width;
            newLength = this.length;
        }
        
        // Rotate the blocks into the temp arrays
        for ( int x = 0; x < this.width; ++x ) {
            for ( int y = 0; y < this.height; ++y ) {
                for ( int z = 0; z < this.length; ++z ) {
                    
                    int index = y * this.width * this.length + z * this.width + x;
                    
                    // Rotate coordinates around the axis
                    int rx, ry, rz;
                    if ( axis == 0 ) {
                        rx = x;
                        ry = clockwise ? newHeight - 1 - z : z;
                        rz = clockwise ? y : newLength - 1 - y;
                    } else if ( axis == 1 ) {
                        rx = clockwise ? newWidth - 1 - z : z;
                        ry = y;
                        rz = clockwise ? x : newLength - 1 - x;
                    } else {
                        rx = clockwise ? newWidth - 1 - y : y;
                        ry = clockwise ? x : newHeight - 1 - x;
                        rz = z;
                    }
                    int rIndex = ry * newWidth * newLength + rz * newWidth + rx;
                    
                    // Set the data
                    tempBlocks[ rIndex ] = this.blocks[ index ];
                    tempData[ rIndex ] = this.data[ index ];
                }
            }
        }
        
        // Apply the results
        this.width = newWidth;
        this.height = newHeight;
        this.length = newLength;
        this.blocks = tempBlocks;
        this.data = tempData;
    }
    
    @SuppressWarnings( "resource" )
    public static Schematic load( File file ) throws IOException {
        FileInputStream stream = new FileInputStream( file );
        NBTInputStream nbtStream = new NBTInputStream( stream );
        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        if ( !schematicTag.getName().equals( "Schematic" ) ) {
            throw new IllegalArgumentException( "Tag \"Schematic\" does not exist or is not first" );
        }
        
        Map<String, Tag> schematic = schematicTag.getValue();
        if ( !schematic.containsKey( "Blocks" ) ) {
            throw new IllegalArgumentException( "Schematic file is missing a \"Blocks\" tag" );
        }
        
        short width = Schematic.getChildTag( schematic, "Width", ShortTag.class ).getValue();
        short length = Schematic.getChildTag( schematic, "Length", ShortTag.class ).getValue();
        short height = Schematic.getChildTag( schematic, "Height", ShortTag.class ).getValue();
        
        String materials = Schematic.getChildTag( schematic, "Materials", StringTag.class ).getValue();
        if ( !materials.equals( "Alpha" ) ) {
            throw new IllegalArgumentException( "Schematic file is not an Alpha schematic" );
        }
        
        byte[] blockId = Schematic.getChildTag( schematic, "Blocks", ByteArrayTag.class ).getValue();
        byte[] blockData = Schematic.getChildTag( schematic, "Data", ByteArrayTag.class ).getValue();
        byte[] addId = new byte[0];
        short[] blocks = new short[blockId.length]; // Have to later combine IDs
        
        // We support 4096 block IDs using the same method as vanilla Minecraft,
        // where
        // the highest 4 bits are stored in a separate byte array.
        if ( schematic.containsKey( "AddBlocks" ) ) {
            addId = Schematic.getChildTag( schematic, "AddBlocks", ByteArrayTag.class ).getValue();
        }
        
        // Combine the AddBlocks data with the first 8-bit block ID
        for ( int index = 0; index < blockId.length; index++ ) {
            if ( index >> 1 >= addId.length ) { // No corresponding AddBlocks
                // index
                blocks[ index ] = (short) ( blockId[ index ] & 0xFF );
            } else {
                if ( ( index & 1 ) == 0 ) {
                    blocks[ index ] = (short) ( ( ( addId[ index >> 1 ] & 0x0F ) << 8 ) + ( blockId[ index ] & 0xFF ) );
                } else {
                    blocks[ index ] = (short) ( ( ( addId[ index >> 1 ] & 0xF0 ) << 4 ) + ( blockId[ index ] & 0xFF ) );
                }
            }
        }
        stream.close();
        nbtStream.close();
        return new Schematic( blocks, blockData, width, length, height );
    }
    
    /**
     * Get child tag of a NBT structure.
     *
     * @param items
     *            The parent tag map
     * @param key
     *            The name of the tag to get
     * @param expected
     *            The expected type of the tag
     * @return child tag casted to the expected type\ expected type
     */
    private static <T extends Tag> T getChildTag( Map<String, Tag> items, String key, Class<T> expected ) throws IllegalArgumentException {
        if ( !items.containsKey( key ) ) {
            throw new IllegalArgumentException( "Schematic file is missing a \"" + key + "\" tag" );
        }
        Tag tag = items.get( key );
        if ( !expected.isInstance( tag ) ) {
            throw new IllegalArgumentException( key + " tag is not of tag type " + expected.getName() );
        }
        return expected.cast( tag );
    }
    
    //custom!
    public boolean isEmpty( Location minLoc ) {
        World w = minLoc.getWorld();
        for ( int x = minLoc.getBlockX(); x <= minLoc.getBlockX() + this.width; x++ ) {
            for ( int y = minLoc.getBlockY(); y <= minLoc.getBlockX() + this.height; y++ ) {
                for ( int z = minLoc.getBlockZ(); z <= minLoc.getBlockZ() + this.length; z++ ) {
                    
                    if ( w.getBlockAt( x, y, z ).getType() != Material.AIR ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
