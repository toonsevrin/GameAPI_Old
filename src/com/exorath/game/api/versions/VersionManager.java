package com.exorath.game.api.versions;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.exorath.game.GameAPI;

// TODO: THINK THIS VERSION CONTROL OUT, FINISH, DOCUMENT AND IMPROVE THIS FILE.
// (I will do this)
// -Toon
@SuppressWarnings("unused")
public class VersionManager {

    private static final String VERSIONS_TABLENAME = "dataversions";
    private static final String COLUMN_MAPS = "maps";
    private static final String COLUMN_CONFIGS = "configs";
    private static final String COLUMN_RESOURCES = "resources";

    private String pluginName;

    //Whether or not versions are loaded
    private boolean loaded = false;

    //Main GameAPI data folder
    private File dataFolder;

    //Local file folders
    private File mapsFolder;
    private File configsFolder;
    private File resourcesFolder;

    //Remote file versions
    private VersionFiles rMapsVersions;
    private VersionFiles rConfigsVersions;
    private VersionFiles rResourcesVersions;

    public VersionManager(String pluginName) {
        this.pluginName = pluginName;

        loadLocal();
        fetchRemote();
    }

    private void loadLocal() {
        dataFolder = GameAPI.getInstance().getDataFolder();

        mapsFolder = new File(dataFolder + "/maps");
        configsFolder = new File(dataFolder + "/configs");
        resourcesFolder = new File(dataFolder + "/resources");
    }

    /**
     * Fetch file versions from database
     * TODO: check whether rs.getString() returns null if it doesn't exist.
     */
    private void fetchRemote() {
        ResultSet rs = GameAPI.getSQLManager()
                .executeQuery("SELECT * FROM " + VERSIONS_TABLENAME + " WHERE plugin='" + pluginName + "';");
        try {
            if (rs.next()) {
                rMapsVersions = new VersionFiles(rs.getString(COLUMN_MAPS));
                rConfigsVersions = new VersionFiles(rs.getString(COLUMN_CONFIGS));
                rResourcesVersions = new VersionFiles(rs.getString(COLUMN_RESOURCES));
            } else {
                GameAPI.printConsole(
                        "Version manager didn't find a row with the plugin name in table " + VERSIONS_TABLENAME);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
