package me.lolok.crates.commands.subcommands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface Subcommand {

    /**
     * Gets the description of this subcommand
     * @return command description
     */
    String getDescription();

    /**
     * Checks if console is allowed to execute this command
     * @return true if is allowed
     */
    default boolean isAllowedConsole() {
        return true;
    }

    /**
     * Gets the permission to execute this subcommand
     * @return command permission
     */
    default String getPermission() {
        return "";
    }

    /**
     * Gets the description of this subcommand
     * @return command description
     */
    default String getArguments() {
        return "";
    }

    /**
     * Execute this subcommand
     * @param sender that execute this command
     * @param args passed to this command
     */
    void execute(CommandSender sender, String[] args);

    /**
     * When sender trigger the tab complete this
     * method return the possible completions
     * @param args already written
     * @return completions
     */
    default List<String> getTabComplete(String[] args) {
        return new ArrayList<>();
    }
}
