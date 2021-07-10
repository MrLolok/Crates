package me.lolok.crates.commands.subcommands;

import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.views.view.impl.DeleteCrateView;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteCommand implements Subcommand {
    private final ICrateService service = CratesPlugin.getInstance().getCrateService();

    @Getter
    private final String description = "Delete a crate", permissions = "crates.admin.delete", arguments = "<name>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (service.getCrate(args[0]) == null) {
            Message.CRATE_DOESNT_EXIST.send(sender, "crate", args[0]);
            return;
        }

        new DeleteCrateView(args[0]);
    }

    @Override
    public List<String> getTabComplete(String[] args) {
        return service.getCrates().stream().map(Crate::getName).filter(s -> s.toUpperCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
    }
}
