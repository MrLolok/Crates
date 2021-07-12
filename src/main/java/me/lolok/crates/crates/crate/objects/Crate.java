package me.lolok.crates.crates.crate.objects;

import me.lolok.crates.crates.crate.animations.AnimationType;
import me.lolok.crates.crates.crate.prizes.CratePrize;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface Crate {

    /**
     * Gets the name of this crate
     * @return name of the crate
     */
    String getName();

    /**
     * Gets the item that represents this crate
     * @return item to open this crate
     */
    ItemStack getItem();

    /**
     * Gets the animation type used to
     * display an animation on opening phase
     * @return animation identifier
     */
    AnimationType getAnimationType();

    /**
     * Gets all {@link CratePrize} that could be
     * found opening this crate
     * @return a set of {@link CratePrize}
     */
    Set<CratePrize> getPrizes();

    /**
     * Update the {@link AnimationType} of this crate
     * and edit the crate on mongo
     * @param type to set
     */
    void setAnimationType(AnimationType type);

    /**
     * Add a {@link CratePrize} to this crate
     * and update the prizes collection on mongo
     * @param prize to add
     */
    void addPrize(CratePrize prize);

    /**
     * Remove a {@link CratePrize} from this crate
     * and update the prizes collection on mongo
     * @param prize to remove
     */
    void removePrize(CratePrize prize);

    /**
     * Extract a random {@link CratePrize} from this
     * crate. The prize could be null if the sum of
     * the chances of prizes doesn't reach 100%
     */
    @Nullable CratePrize getRandomPrize();

}
