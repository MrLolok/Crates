package me.lolok.crates.commands.subcommands;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.commands.ICommandService;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;

import java.util.Map;

@RequiredArgsConstructor
public class HelpCommand implements Subcommand {
    private final ICommandService commandManager;

    @Getter
    private final String description = "Show commands list";

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("§m----§b§m----§3§l[ §b§lCRATES §3§l]§b§m----§f§m-----");
        Map<String, Subcommand> commands = commandManager.getCommands();
        commands.forEach((name, command) -> sendCommand(sender, name, command));
    }

    private void sendCommand(CommandSender sender, String name, Subcommand subcommand) {
        TextComponent component = new TextComponent("§3• §b/crates " + name + " " + (subcommand.getArguments().isEmpty() ? "" : subcommand.getArguments() + " ") + "§8- §f" + subcommand.getDescription());
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§3§l> §bExecute this command")));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/crates " + name + " "));
        sender.spigot().sendMessage(component);
    }
}
