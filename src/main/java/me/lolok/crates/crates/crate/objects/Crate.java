package me.lolok.crates.crates.crate.objects;

import me.lolok.crates.items.objects.CrateItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Crate {

    String getName();

    ItemStack getItem();

    Set<CrateItem> getPrizes();

    void addPrize(CrateItem item);

    void removePrize(CrateItem item);

    @Nullable ItemStack getRandomPrize();

}
