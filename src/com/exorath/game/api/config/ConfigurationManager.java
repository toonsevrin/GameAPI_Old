package com.exorath.game.api.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.exorath.game.GameAPI;

/**
 * @author Nick Robson
 *         Edited by TOON SEVRIN, needs Reviewing by Nick
 */
public class ConfigurationManager {

    public static final ConfigurationManager INSTANCE = new ConfigurationManager();

    private ConfigurationManager() {
    }

    public FileConfiguration getResource(Plugin plugin, String resource) {
        InputStream res = plugin.getResource(resource);
        if (res == null)
            return null;
        return YamlConfiguration.loadConfiguration(new InputStreamReader(res));
    }

    public void saveResource(Plugin plugin, String resource, String file, boolean overwrite) {
        this.saveResource(plugin, resource, this.getConfigFile(plugin, file), overwrite);
    }

    public void saveResource(Plugin plugin, String resource, File file, boolean overwrite) {
        InputStream in = plugin.getResource(resource);
        if (in == null && !resource.endsWith(".yml"))
            in = plugin.getResource(resource + ".yml");
        if (in == null)
            return;
        try {
            CopyOption[] options = overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{};
            Files.copy(in, file.toPath(), options);
        } catch (IOException e) {e.printStackTrace();}
    }

    public File getConfigFile(Plugin plugin, String file) {
        return new File(plugin.getDataFolder(), file);
    }

    public FileConfiguration getConfig(Plugin plugin, String file) {
        return getConfig(getConfigFile(plugin, file));
    }

    public FileConfiguration getConfig(File f) {
        return YamlConfiguration.loadConfiguration(f);
    }

}
