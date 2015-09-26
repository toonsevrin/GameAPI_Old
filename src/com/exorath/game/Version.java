package com.exorath.game;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Nick Robson
 */
public class Version {

    private static final Map<Integer, Version> versions = Maps.newHashMap();

    public static Version from(String name, String version, int versionNumber, int apiVersion) {
        if (versionNumber > 0 && Version.versions.containsKey(versionNumber))
            return Version.versions.get(versionNumber);
        return new Version(name, version, versionNumber, apiVersion);
    }

    private final String name, version;
    private final int versionNumber, apiVersion;

    private Version(String name, String version, int versionNumber, int apiVersion) {
        this.name = name;
        this.version = version;
        this.versionNumber = versionNumber;
        this.apiVersion = apiVersion;

        if (version != null)
            Version.versions.put(versionNumber, this);
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public int getAPIVersion() {
        return apiVersion;
    }

    public boolean isCompatible(Version version) {
        return version.getAPIVersion() == getAPIVersion();
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o))
            return true;
        if (o instanceof Version) {
            Version v = (Version) o;
            if (v.getName().equals(getName()) && v.getVersionNumber() == getVersionNumber())
                return true;
        }
        return false;
    }

}
