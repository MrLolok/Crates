package me.lolok.crates.configurations.messages;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.files.MessagesConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

@RequiredArgsConstructor
public enum Message {
    CRATE_WON("crate.won"),
    CRATE_LOST("crate.lost"),

    EDIT_CHANCE_MESSAGE("edit.chance.message"),
    EDIT_CHANCE_TITLE("edit.chance.title"),
    EDIT_CHANCE_SUBTITLE("edit.chance.subtitle"),

    GAVE("give.gave"),
    TOOK("give.took"),

    LIST_HEADER("list.header"),
    LIST_ENTRY_TEXT("list.entry.text"),
    LIST_ENTRY_HOVER("list.entry.hover"),
    LIST_NEXT_BUTTON_TEXT("list.next-button.text"),
    LIST_NEXT_BUTTON_HOVER("list.next-button.hover"),

    UNKNOWN_COMMAND("errors.unknown-command"),
    CONSOLE_DISALLOWED("errors.console-disallowed"),
    PERMISSION_DENIED("errors.permission-denied"),
    OFFLINE_PLAYER("errors.offline-player"),
    DAILY_MAX_REACHED("errors.daily-max-reached"),
    OPEN_DELAY("errors.open-delay"),
    CRATE_ALREADY_EXIST("errors.create-already-exist"),
    CRATE_DOESNT_EXIST("errors.crate-doesnt-exist"),
    NO_ITEM("errors.no-item"),
    ALREADY_EDITING("errors.already-editing"),
    INVALID_CHANCE("errors.invalid-chance"),
    INVALID_PAGE("errors.invalid-page");

    private final static MessagesConfiguration MESSAGES_CONFIGURATION = (MessagesConfiguration) CratesPlugin.getInstance().getConfigurationService().getConfiguration("messages");
    private final static boolean PLACEHOLDERS_ENABLED = Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");

    private final String path;

    public String getMessage(String... placeholders) {
        String message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MESSAGES_CONFIGURATION.getConfiguration().getString(path)));
        if (placeholders != null && placeholders.length % 2 == 0) {
            for (int i = 0; i < placeholders.length; i++) {
                if (i % 2 != 0) continue;
                String placeholder = "%" + placeholders[i] + "%", value = placeholders[i + 1];
                message = message.replace(placeholder, value);
            }
        }
        return message;
    }

    public void send(CommandSender target, String... placeholders) {
        String message = PLACEHOLDERS_ENABLED && target instanceof Player ? PlaceholderAPI.setPlaceholders((Player) target, getMessage(placeholders)) : getMessage(placeholders);
        target.sendMessage(message);
    }
}
