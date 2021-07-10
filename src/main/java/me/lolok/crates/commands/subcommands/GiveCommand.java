package me.lolok.crates.commands.subcommands;

import com.cryptomorin.xseries.XSound;
import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class GiveCommand implements Subcommand {
    private final ICrateService service = CratesPlugin.getInstance().getCrateService();

    @Getter
    private final String description = "Give a crate to a player", permission = "crates.admin.give", arguments = "<crate> <player>";

    @Override
    public void execute(CommandSender sender, String[] args) {
        Crate crate = service.getCrate(args[0]);
        if (crate == null) {
            Message.CRATE_DOESNT_EXIST.send(sender, "crate", args[0]);
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            Message.OFFLINE_PLAYER.send(sender, "player", args[1]);
            return;
        }

        Message.GAVE.send(sender, "crate", crate.getName(), "player", target.getName());
        Message.TOOK.send(target, "crate", crate.getName());
        target.playSound(target.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1F, 2F);
        target.getInventory().addItem(crate.getItem());
    }

    @Override
    public List<String> getTabComplete(String[] args) {
        if (args.length < 2)
            return service.getCrates().stream().map(Crate::getName).filter(s -> s.toUpperCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());

        return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
    }
}
