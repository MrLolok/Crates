package me.lolok.crates.commands.subcommands;

import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.views.view.impl.DeleteCrateView;
import me.lolok.crates.views.view.impl.EditCrateView;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class EditCommand implements Subcommand {
    private final ICrateService service = CratesPlugin.getInstance().getCrateService();

    @Getter
    private final String description = "Edit prizes and chances of a crate", permissions = "crates.admin.edit", arguments = "<name>";

    @Getter
    private final boolean allowedConsole = false;

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (service.isEditing(player)) {
            Message.ALREADY_EDITING.send(player, "crate", args[0]);
            return;
        }

        Crate crate = service.getCrate(args[0]);
        if (crate == null) {
            Message.CRATE_DOESNT_EXIST.send(player, "crate", args[0]);
            return;
        }

        new EditCrateView(crate).open(player);
    }

    @Override
    public List<String> getTabComplete(String[] args) {
        return service.getCrates().stream().map(Crate::getName).filter(s -> s.toUpperCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
    }
}
