package me.lolok.crates.configurations;

import com.google.common.io.ByteStreams;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.CratesPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

@RequiredArgsConstructor
public class ConfigurationFileHandler implements IConfigurationFileHandler {
    private final CratesPlugin plugin;

    /**
     * Gets an item's configuration file
     *
     * @param identifier of the item
     * @param copy       true if the plugin should copy
     *                   defaults configuration content
     * @return the config file of the item
     * @throws RuntimeException if {@link File} or {@link ConfigurationFileHandler#copyContents(InputStream, File)} throw IOException
     */
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

    /**
     * Gets an item's YAML configuration file
     *
     * @param identifier of the item
     * @param copy       true if the plugin should copy
     *                   defaults configuration content
     * @return the config YAML of the item
     * @see ConfigurationFileHandler#getFile(String, boolean)
     */
    @Override
    public YamlConfiguration getConfig(String identifier, boolean copy) {
        return YamlConfiguration.loadConfiguration(getFile(identifier, copy));
    }

    /**
     * Copy contents from InputStream in another file
     *
     * @param in   InputStream from which to copy contents
     * @param file File to copy the contents to
     * @throws IOException if {@link FileOutputStream} or {@link ByteStreams#copy(InputStream, OutputStream)} throw IOException
     */
    @SuppressWarnings("UnstableApiUsage")
    private void copyContents(InputStream in, File file) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            ByteStreams.copy(in, out);
        }
    }
}
