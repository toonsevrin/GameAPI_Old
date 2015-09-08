package com.exorath.game.api.versions;

import com.exorath.game.GameAPI;

import java.util.HashMap;

/**
 * Created by Toon on 23/06/2015.
 */
public class VersionFiles {

    private static final String FILE_VERSION_SEPERATOR = ":";
    private static final String FILE_FILE_SEPERATOR = ",";
    private HashMap<String, String> versions = new HashMap<String, String>();//Hashmap with file name and file version as value

    public VersionFiles(String databaseString) {
        if (databaseString == null)
            return;
        if (databaseString == "")
            return;
        if (!databaseString.contains(FILE_VERSION_SEPERATOR))
            return;
        String[] filesAndVersions = databaseString.split(FILE_FILE_SEPERATOR);

        for (String fileAndVersion : filesAndVersions) {
            if (fileAndVersion == null || !fileAndVersion.contains(FILE_VERSION_SEPERATOR)) {
                GameAPI.error("Version manager error in " + fileAndVersion);
                continue;
            }

            String file = fileAndVersion.split(FILE_VERSION_SEPERATOR)[0];
            String version = fileAndVersion.split(FILE_VERSION_SEPERATOR)[1];

            if (file == null || version == null || file.equals("") || version.equals("")) {
                GameAPI.error("Version manager error in " + databaseString + " at " + fileAndVersion);
                continue;
            }
            addFileAndVersion(file, version);
        }
    }

    public boolean isUpToDate(String fileName, String version) {
        if (!versions.containsKey(fileName))
            return false;//If file name wasn't loaded from database
        if (!versions.get(fileName).equals(version))
            return false;//If versions don't match
        return true;//Versions match.
    }

    public void addFileAndVersion(String name, String version) {
        versions.put(name, version);
    }
}
