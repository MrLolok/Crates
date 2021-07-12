package me.lolok.crates.crates.crate.animations;

import me.lolok.crates.crates.crate.objects.Crate;
import org.bukkit.entity.Player;

public class QuickAnimation extends Animation {
    public QuickAnimation() {
        super("QUICK");
    }

    @Override
    public void start(Crate crate, Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
        sendPrize(crate.getRandomPrize(), player);
    }
}
