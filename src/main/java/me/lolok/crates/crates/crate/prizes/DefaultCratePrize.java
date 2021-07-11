package me.lolok.crates.crates.crate.prizes;

import lombok.*;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@RequiredArgsConstructor
public class DefaultCratePrize implements CratePrize {
    @Getter
    private final ItemStack item;

    @Getter @Setter
    private double chance = 0D;
}
