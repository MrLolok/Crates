package me.lolok.crates.configurations;

import com.google.common.io.ByteStreams;
import me.lolok.crates.configurations.files.Configuration;
import me.lolok.crates.crates.Service;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public interface IConfigurationService extends Service {

    @Nullable Configuration getConfiguration(String name);

    void register(String name, Configuration configuration);

    /**
     * Gets an item's configuration file
     *
     * @param identifier of the item
     * @param copy       true if the plugin should copy
     *                   defaults configuration content
     * @return the config file of the item
     * @throws RuntimeException if {@link File} or {@link IConfigurationService#copyContents(InputStream, File)} throw IOException
     */
    File getFile(String identifier, boolean copy) throws RuntimeException;

    /**
     * Gets an item's YAML configuration file
     *
     * @param identifier of the item
     * @param copy       true if the plugin should copy
     *                   defaults configuration content
     * @return the config YAML of the item
     * @see IConfigurationService#getFile(String, boolean)
     */
    YamlConfiguration getConfig(String identifier, boolean copy);

    /**
     * Copy contents from InputStream in another file
     *
     * @param in   InputStream from which to copy contents
     * @param file File to copy the contents to
     * @throws IOException if {@link FileOutputStream} or {@link ByteStreams#copy(InputStream, OutputStream)} throw IOException
     */
    void copyContents(InputStream in, File file) throws IOException;

}
