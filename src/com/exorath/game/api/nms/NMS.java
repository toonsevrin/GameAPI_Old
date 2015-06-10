package com.exorath.game.api.nms;

/**
 * @author Nick Robson
 */
public class NMS {
    
    private static NMSProvider provider;
    
    public static NMSProvider get() {
        return NMS.provider;
    }
    
    public static void set( NMSProvider provider ) {
        if ( NMS.provider == null ) {
            NMS.provider = provider;
        }
    }
    
}
