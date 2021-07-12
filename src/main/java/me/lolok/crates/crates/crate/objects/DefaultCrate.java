package me.lolok.crates.crates.crate.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lolok.crates.crates.crate.ICrateService;
import me.lolok.crates.crates.crate.animations.AnimationType;
import me.lolok.crates.crates.crate.prizes.CratePrize;
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
    protected AnimationType animationType;

    @Getter
    protected Set<CratePrize> prizes = new HashSet<>();

    @Override
    public void setAnimationType(AnimationType type) {
        this.animationType = type;
        service.save(this);
    }

    @Override
    public void addPrize(CratePrize prize) {
        prizes.add(prize);
        service.save(this);
    }

    @Override
    public void removePrize(CratePrize prize) {
        prizes.remove(prize);
        service.save(this);
    }

    @Override @Nullable
    public CratePrize getRandomPrize() {
        double chance = new Random().nextDouble() * 100D;
        double cumulative = 0.0;
        for (CratePrize prize : prizes) {
            cumulative += prize.getChance();
            if (chance < cumulative)
                return prize;
        }

        return null;
    }
}
