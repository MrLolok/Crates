package me.lolok.crates.items.objects;

import org.bukkit.inventory.ItemStack;

public interface CrateItem {

    ItemStack getItem();

    double getChance();

    void setChance(double chance);

}
