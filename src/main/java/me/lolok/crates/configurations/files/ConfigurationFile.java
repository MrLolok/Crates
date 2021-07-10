package me.lolok.crates.configurations.files;

import me.lolok.crates.configurations.IConfigurationFileHandler;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class ConfigurationFile {
    public ConfigurationFile(IConfigurationFileHandler handler, String name, boolean copy) {
        initialize(handler.getConfig(name, copy));
    }

    public abstract void initialize(YamlConfiguration configuration);
}
