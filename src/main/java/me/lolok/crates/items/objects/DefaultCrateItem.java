package me.lolok.crates.items.objects;

import lombok.*;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@RequiredArgsConstructor
public class DefaultCrateItem implements CrateItem {
    @Getter
    private final ItemStack item;

    @Getter @Setter
    private double chance = 0D;
}
