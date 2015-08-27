package com.exorath.game;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Nick Robson
 */
public class Version {

    private static final Map<String, Version> versions = Maps.newHashMap();

    public static Version from( String name, String version, int versionNumber, int apiVersion ) {
        if ( version != null && Version.versions.containsKey( version ) ) {
            return Version.versions.get( version );
        }
        return new Version( name, version, versionNumber, apiVersion );
    }

    private final String name, version;
    private final int versionNumber, apiVersion;

    private Version( String name, String version, int versionNumber, int apiVersion ) {
        this.name = name;
        this.version = version;
        this.versionNumber = versionNumber;
        this.apiVersion = apiVersion;

        if ( version != null ) {
            Version.versions.put( version, this );
        }
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public int getAPIVersion() {
        return this.apiVersion;
    }

    public boolean isCompatible( Version version ) {
        return version.getAPIVersion() == this.getAPIVersion();
    }

    @Override
    public boolean equals( Object o ) {
        if ( o instanceof Version ) {
            Version v = (Version) o;
            if ( v.getName().equals( this.getName() ) && v.getVersionNumber() == this.getVersionNumber() ) {
                return true;
            }
        }
        return false;
    }

}
