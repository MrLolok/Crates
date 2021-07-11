package me.lolok.crates.configurations;

import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.files.Configuration;
import me.lolok.crates.configurations.files.DatabaseConfiguration;
import me.lolok.crates.configurations.files.MessagesConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ConfigurationService implements IConfigurationService {
    private final CratesPlugin plugin;
    private final Map<String, Configuration> configurations = new HashMap<>();

    @Override
    public void enable() {
        register("database", new DatabaseConfiguration(this));
        register("messages", new MessagesConfiguration(this));
    }

    @Override
    public void disable() {

    }

    @Override @Nullable
    public Configuration getConfiguration(String name) {
        return configurations.get(name);
    }

    @Override
    public void register(String name, Configuration configuration) {
        if (configurations.containsKey(name))
            throw new IllegalStateException(String.format("The configuration %s is already registered", name));

        configurations.put(name, configuration);
    }

    @Override
    public File getFile(String identifier, boolean copy) {
        File file = new File(plugin.getDataFolder(), identifier + ".yml");
        if (file.exists() || !copy) return file;

        try {
            if (file.createNewFile())
                try (InputStream in = getClass().getClassLoader().getResourceAsStream(String.format("%s.yml", identifier))) {
                    copyContents(in, file);
                }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Can't load default config file for item %s", identifier), e);
        }

        return file;
    }

    @Override
    public YamlConfiguration getConfig(String identifier, boolean copy) {
        return YamlConfiguration.loadConfiguration(getFile(identifier, copy));
    }

    @Override @SuppressWarnings("UnstableApiUsage")
    public void copyContents(InputStream in, File file) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            ByteStreams.copy(in, out);
        }
    }
}
