package com.exorath.game.api.nms;

/**
 * @author Nick Robson
 */
public class NMS {

    private static NMSProvider provider = null;

    public static NMSProvider get() {
        if (NMS.provider == null) {
            throw new UnsupportedOperationException("There is no NMSProvider for this version of Minecraft.");
        }
        return NMS.provider;
    }

    public static void set(NMSProvider provider) {
        if (NMS.provider == null && provider != null) {
            NMS.provider = provider;
        }
    }

}
