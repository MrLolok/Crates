package me.lolok.crates.commands.subcommands;

import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.views.view.impl.CreateCrateView;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements Subcommand {
    private final ICrateService service = CratesPlugin.getInstance().getCrateService();

    @Getter
    private final String description = "Create a new crate", permissions = "crates.admin.crate", arguments = "<name>";

    @Getter
    private final boolean allowedConsole = false;

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (service.getCrate(args[0]) != null) {
            Message.CRATE_ALREADY_EXIST.send(player);
            return;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            Message.NO_ITEM.send(player);
            return;
        }

        new CreateCrateView(args[0]).open(player);
    }
}
