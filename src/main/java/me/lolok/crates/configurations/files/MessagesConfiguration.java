package me.lolok.crates.configurations.files;

import lombok.Getter;
import me.lolok.crates.configurations.IConfigurationFileHandler;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessagesConfiguration extends ConfigurationFile {
    @Getter
    private YamlConfiguration configuration;

    public MessagesConfiguration(IConfigurationFileHandler handler) {
        super(handler, "messages", true);
    }

    @Override
    public void initialize(YamlConfiguration configuration) {
        this.configuration = configuration;
    }
}
