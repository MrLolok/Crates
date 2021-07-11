package me.lolok.crates.crates.crate.prizes;

import org.bukkit.inventory.ItemStack;

public interface CratePrize {

    /**
     * Gets the {@link ItemStack} that represents
     * this prize
     * @return item of this prize
     */
    ItemStack getItem();

    /**
     * Gets the chance to find this prize in a
     * {@link me.lolok.crates.crates.crate.objects.Crate}
     * @return chance of this prize
     */
    double getChance();

    /**
     * Sets the chance to find this prize in a
     * {@link me.lolok.crates.crates.crate.objects.Crate}
     * @param chance to set
     */
    void setChance(double chance);

}
