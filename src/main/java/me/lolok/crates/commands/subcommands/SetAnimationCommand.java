package me.lolok.crates.commands.subcommands;

import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.animations.AnimationType;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.views.view.impl.CreateCrateView;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetAnimationCommand implements Subcommand {
    private final ICrateService service = CratesPlugin.getInstance().getCrateService();

    @Getter
    private final String description = "Set the animation type of a crate", permissions = "crates.admin.setanimation", arguments = "<crate> <animation>";

    @Getter
    private final boolean allowedConsole = false;

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Crate crate = service.getCrate(args[0]);
        if (crate == null) {
            Message.CRATE_DOESNT_EXIST.send(player);
            return;
        }

        AnimationType type;
        try {
            type = AnimationType.valueOf(args[1]);
        } catch (IllegalArgumentException e) {
            return;
        }

        crate.setAnimationType(type);
        Message.ANIMATION_SET.send(sender, "animation", type.name(), "crate", crate.getName());
    }
}
