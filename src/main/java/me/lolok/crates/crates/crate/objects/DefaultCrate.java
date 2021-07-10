package me.lolok.crates.crates.crate.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.items.objects.CrateItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
public class DefaultCrate implements Crate {
    private final ICrateService service;

    @Getter
    protected final String name;

    @Getter
    protected final ItemStack item;

    @Getter
    protected Set<CrateItem> prizes = new HashSet<>();

    @Override
    public void addPrize(CrateItem item) {
        prizes.add(item);
        service.save(this);
    }

    @Override
    public void removePrize(CrateItem item) {
        prizes.remove(item);
        service.save(this);
    }

    @Override @Nullable
    public ItemStack getRandomPrize() {
        double chance = new Random().nextDouble() * 100D;
        double cumulative = 0.0;
        for (CrateItem prize : prizes) {
            cumulative += prize.getChance();
            if (chance < cumulative)
                return prize.getItem();
        }

        return null;
    }
}
