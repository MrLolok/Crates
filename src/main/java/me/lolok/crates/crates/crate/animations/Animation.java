package me.lolok.crates.crates.crate.animations;

import com.cryptomorin.xseries.XSound;
import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.messages.Message;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.crate.prizes.CratePrize;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This is an animation class that animate the opening of a {@link Crate}
 * @see me.lolok.crates.crates.crate.ICrateService#open(Crate, Player, Animation)
 */
public abstract class Animation {
    protected final static List<Material> GLASSES = CratesPlugin.getInstance().getConfig().getStringList("animation.glasses").stream().map(Material::getMaterial).collect(Collectors.toList());
    protected final static int ANIMATION_SPEED = CratesPlugin.getInstance().getConfig().getInt("animation.speed");

    @Getter
    protected final String name;

    public Animation(String name) {
        this.name = name;
    }

    /**
     * Sends the {@link org.bukkit.inventory.ItemStack} of the prize won
     * and a message with a sound
     * @param prize won
     * @param player to send items to
     */
    protected void sendPrize(@Nullable CratePrize prize, Player player) {
        Message message = prize == null ? Message.CRATE_LOST : Message.CRATE_WON;
        message.send(player, "item_name", prize != null ? prize.getItem().getItemMeta().hasDisplayName() ? prize.getItem().getItemMeta().getDisplayName() : prize.getItem().getType().name() : "Nothing", "item_chance", prize != null ? String.valueOf(prize.getChance()) : "");
        player.playSound(player.getLocation(), Objects.requireNonNull(XSound.ENTITY_PLAYER_LEVELUP.parseSound()), 1F, 2F);

        if (prize != null)
            player.getInventory().addItem(prize.getItem());
    }

    /**
     * Start the animation
     * @param player to start the animation to
     */
    public abstract void start(Crate crate, Player player);
}
