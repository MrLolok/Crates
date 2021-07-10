package me.lolok.crates;

import lombok.Getter;
import me.lolok.crates.commands.CommandManager;
import me.lolok.crates.configurations.ConfigurationFileHandler;
import me.lolok.crates.configurations.IConfigurationFileHandler;
import me.lolok.crates.configurations.files.DatabaseConfiguration;
import me.lolok.crates.crates.crate.CrateService;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.database.IMongoDBManager;
import me.lolok.crates.database.MongoDBManager;
import me.lolok.crates.views.listeners.ViewDispatcherListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CratesPlugin extends JavaPlugin {
    @Getter private static CratesPlugin instance;

    @Getter
    private IMongoDBManager mongoDBManager;

    @Getter
    private IConfigurationFileHandler configurationFileHandler;

    @Getter
    private ICrateService crateService;

    @Override
    public void onLoad() {
        instance = this;

        // Load default config
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        // Initialize the configuration file handler
        this.configurationFileHandler = new ConfigurationFileHandler(this);

        // Initialize the MongoDB manager
        this.mongoDBManager = new MongoDBManager(new DatabaseConfiguration(configurationFileHandler));

        // Initialize the crate service
        this.crateService = new CrateService(this);

        // Register commands
        CommandManager commandManager = new CommandManager();
        Objects.requireNonNull(getCommand("crates")).setExecutor(commandManager);

        // Register view dispatcher listener
        getServer().getPluginManager().registerEvents(new ViewDispatcherListener(), this);

        // Plugin enabled successful
        Bukkit.getConsoleSender().sendMessage("§a| Plugin: " + getDescription().getName());
        Bukkit.getConsoleSender().sendMessage("§a| Version: " + getDescription().getVersion());
    }
}
