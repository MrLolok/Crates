package me.lolok.crates.crates.crate.animations;

import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.crate.prizes.CratePrize;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is an animation class. Is instantiated to animate
 * the opening of a {@link Crate}
 * @see me.lolok.crates.crates.crate.ICrateService#open(Crate, Player, Animation)
 */
public abstract class Animation {
    protected final static List<Material> GLASSES = CratesPlugin.getInstance().getConfig().getStringList("animation.glasses").stream().map(Material::getMaterial).collect(Collectors.toList());
    protected final static int ANIMATION_SPEED = CratesPlugin.getInstance().getConfig().getInt("animation.speed");

    @Getter
    protected final Crate crate;

    @Getter
    protected final CratePrize prize;

    public Animation(Crate crate) {
        this.crate = crate;
        this.prize = crate.getRandomPrize();
    }

    /**
     * Start the animation
     * @param player to start the animation to
     */
    public abstract void start(Player player);

    /**
     * End the animation
     * @param player to end the animation to
     */
    public abstract void end(Player player);
}
