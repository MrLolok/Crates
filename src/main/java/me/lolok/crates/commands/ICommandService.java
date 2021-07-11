package me.lolok.crates.commands;

import me.lolok.crates.commands.subcommands.Subcommand;
import me.lolok.crates.crates.Service;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;

import java.util.Map;

/**
 * Class for managing main command and subcommands.
 */
public interface ICommandService extends CommandExecutor, TabExecutor, Service {

    /**
     * Gets all registered {@link Subcommand}
     * @return key - name of the subcommand | value - subcommand instance
     */
    Map<String, Subcommand> getCommands();

    /**
     * Register a new subcommand
     * @param name of the subcommand
     * @param subcommand to register
     */
    void register(String name, Subcommand subcommand);

}
