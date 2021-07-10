package me.lolok.crates.commands;

import lombok.Getter;
import me.lolok.crates.commands.subcommands.*;
import me.lolok.crates.configurations.messages.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for managing subcommands.
 */
public class CommandManager implements CommandExecutor, TabExecutor {
    @Getter
    private final Map<String, Subcommand> commands = new LinkedHashMap<>();

    public CommandManager() {
        initialize();
    }

    private void initialize() {
        register("help", new HelpCommand(this));
        register("create", new CreateCommand());
        register("delete", new DeleteCommand());
        register("edit", new EditCommand());
        register("give", new GiveCommand());
    }

    public void register(String command, Subcommand subcommand) {
        commands.put(command.toLowerCase(), subcommand);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            commands.get("help").execute(sender, args);
            return true;
        }

        Optional<Map.Entry<String, Subcommand>> matchedCommand = commands.entrySet().stream().filter(entry -> entry.getKey().startsWith(args[0])).findAny();
        if (matchedCommand.isPresent()) {
            Subcommand subcommand = matchedCommand.get().getValue();
            if (!subcommand.isAllowedConsole() && !(sender instanceof Player)) {
                Message.CONSOLE_DISALLOWED.send(sender);
                return true;
            }

            if (!subcommand.getPermission().isEmpty() && !sender.hasPermission(subcommand.getPermission())) {
                Message.PERMISSION_DENIED.send(sender);
                return true;
            }

            String[] param = Arrays.copyOfRange(args, 1, args.length);
            matchedCommand.get().getValue().execute(sender, param);
        } else Message.UNKNOWN_COMMAND.send(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (strings.length <= 1)
            return commands.keySet().stream().filter(cmd -> cmd.toLowerCase().startsWith(strings[0].toLowerCase())).collect(Collectors.toList());
        else {
            Optional<Map.Entry<String, Subcommand>> matchedCommand = commands.entrySet().stream().filter(entry -> entry.getKey().equalsIgnoreCase(strings[0])).findAny();
            if (matchedCommand.isPresent()) {
                String[] param = Arrays.copyOfRange(strings, 1, strings.length);
                return matchedCommand.get().getValue().getTabComplete(param);
            } else
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(strings[strings.length - 1].toLowerCase())).collect(Collectors.toList());
        }
    }
}
