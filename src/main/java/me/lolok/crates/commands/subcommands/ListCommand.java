package me.lolok.crates.commands.subcommands;

import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.views.view.impl.EditCrateView;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCommand implements Subcommand {
    private final static int MAX_ITEMS_PER_PAGE = 10;
    private final ICrateService service = CratesPlugin.getInstance().getCrateService();

    @Getter
    private final String description = "List all crates avaible", permissions = "crates.admin.list";

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<Crate> crates = new LinkedList<>(service.getCrates());
        crates.sort(Comparator.comparing(Crate::getName));

        int page = args.length > 0 ? Integer.parseInt(args[0]) : 1;

        int offset = (page - 1) * MAX_ITEMS_PER_PAGE;
        double d = crates.size() / (double) MAX_ITEMS_PER_PAGE;
        int maxPages = ((int) d) + ((d > (int) d) ? 1 : 0);

        if (offset >= crates.size()) {
            Message.INVALID_PAGE.send(sender, "max_pages", String.valueOf(maxPages));
            return;
        }

        Message.LIST_HEADER.send(sender, "page", String.valueOf(page), "max_pages", String.valueOf(maxPages));

        for (int i = offset; (i - offset) < MAX_ITEMS_PER_PAGE && i < crates.size(); i++) {
            Crate crate = crates.get(i);
            TextComponent comp = new TextComponent(Message.LIST_ENTRY_TEXT.getMessage("crate", crate.getName()));
            comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Message.LIST_ENTRY_HOVER.getMessage())));
            comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/crates give %s %s", crate.getName(), sender.getName())));
            sender.spigot().sendMessage(comp);
        }

        if (page < maxPages) {
            TextComponent comp = new TextComponent(Message.LIST_NEXT_BUTTON_TEXT.getMessage());
            comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Message.LIST_NEXT_BUTTON_HOVER.getMessage())));
            comp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format("/crates list %d", page + 1)));
            sender.spigot().sendMessage(comp);
        }
    }
}
