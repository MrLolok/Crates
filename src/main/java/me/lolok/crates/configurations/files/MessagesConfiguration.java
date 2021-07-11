package me.lolok.crates.configurations.files;

import lombok.Getter;
import me.lolok.crates.configurations.IConfigurationService;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessagesConfiguration extends Configuration {
    @Getter
    private YamlConfiguration configuration;

    public MessagesConfiguration(IConfigurationService handler) {
        super(handler, "messages", true);
    }

    @Override
    public void initialize(YamlConfiguration configuration) {
        this.configuration = configuration;
    }
}
