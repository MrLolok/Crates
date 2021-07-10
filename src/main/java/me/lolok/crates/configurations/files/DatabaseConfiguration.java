package me.lolok.crates.configurations.files;

import lombok.Getter;
import me.lolok.crates.configurations.IConfigurationFileHandler;
import org.bukkit.configuration.file.YamlConfiguration;

@Getter
public class DatabaseConfiguration extends ConfigurationFile {
    private String host, database, user, password;
    private int port;

    public DatabaseConfiguration(IConfigurationFileHandler handler) {
        super(handler, "database", true);
    }

    @Override
    public void initialize(YamlConfiguration configuration) {
        this.host = configuration.getString("host");
        this.database = configuration.getString("database");
        this.user = configuration.getString("authentication.user");
        this.password = configuration.getString("authentication.password");
        this.port = configuration.getInt("port");
    }
}
