package me.lolok.crates.configurations;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public interface IConfigurationFileHandler {
    File getFile(String identifier, boolean copy);

    YamlConfiguration getConfig(String identifier, boolean copy);
}
