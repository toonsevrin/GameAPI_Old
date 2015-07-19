package com.exorath.game.lib.util;

import java.io.File;

/**
 * @author Nick Robson
 */
public class FileUtils {
    
    public static enum FileType {
        FILE,
        DIR;
    }
    
    public static void createIfMissing( File file, FileType type ) {
        if ( !file.exists() ) {
            if ( type == FileType.FILE ) {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch ( Exception ex ) {}
            } else if ( type == FileType.DIR ) {
                file.mkdir();
            }
        }
    }
    
}
