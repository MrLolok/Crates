package me.lolok.crates;

import lombok.Getter;
import me.lolok.crates.commands.CommandService;
import me.lolok.crates.commands.ICommandService;
import me.lolok.crates.configurations.ConfigurationService;
import me.lolok.crates.configurations.IConfigurationService;
import me.lolok.crates.configurations.files.DatabaseConfiguration;
import me.lolok.crates.crates.crate.CrateService;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.database.IMongoDBManager;
import me.lolok.crates.database.MongoDBManager;
import me.lolok.crates.views.listeners.ViewDispatcherListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CratesPlugin extends JavaPlugin {
    @Getter private static CratesPlugin instance;

    @Getter
    private IMongoDBManager mongoDBManager;

    @Getter
    private IConfigurationService configurationService;

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
        // Initialize and enable the configuration file handler
        configurationService = new ConfigurationService(this);
        configurationService.enable();

        // Initialize the MongoDB manager
        mongoDBManager = new MongoDBManager((DatabaseConfiguration) configurationService.getConfiguration("database"));

        // Initialize and enable the crate service
        crateService = new CrateService(this);
        crateService.enable();

        // Register commands
        ICommandService commandService = new CommandService(this);
        commandService.enable();

        // Register view dispatcher listener
        getServer().getPluginManager().registerEvents(new ViewDispatcherListener(), this);

        // Plugin enabled successful
        Bukkit.getConsoleSender().sendMessage("§a| Plugin: " + getDescription().getName());
        Bukkit.getConsoleSender().sendMessage("§a| Version: " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        crateService.disable();
    }
}
