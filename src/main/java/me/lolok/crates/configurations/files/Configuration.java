package me.lolok.crates.configurations.files;

import me.lolok.crates.configurations.IConfigurationService;
import org.bukkit.configuration.file.YamlConfiguration;

public abstract class Configuration {
    public Configuration(IConfigurationService handler, String name, boolean copy) {
        initialize(handler.getConfig(name, copy));
    }

    public abstract void initialize(YamlConfiguration configuration);
}
