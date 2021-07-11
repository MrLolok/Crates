package me.lolok.crates.crates.crate;

import me.lolok.crates.CratesPlugin;
import me.lolok.crates.crates.Service;
import me.lolok.crates.crates.crate.animations.Animation;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.users.ICrateUsersService;
import me.lolok.crates.crates.crate.prizes.CratePrize;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ICrateService extends Service {

    /**
     * Amount of the max crates that could
     * be opened in a day
     */
    int MAX_PER_DAY = CratesPlugin.getInstance().getConfig().getInt("opening.max-per-day");

    /**
     * Delay in seconds to open a crate from
     * another one
     */
    int DELAY = CratesPlugin.getInstance().getConfig().getInt("opening.delay");

    /**
     * Gets the service that manage {@link me.lolok.crates.crates.users.objects.CrateUser}
     * @return users service
     */
    ICrateUsersService getUsersService();

    /**
     * Gets all crates stored into this service
     * @return a set of crates
     */
    Set<Crate> getCrates();

    /**
     * Gets a certain {@link Crate} based on its name
     * @param name of the crate
     * @return crate found
     */
    @Nullable Crate getCrate(String name);

    /**
     * Add a {@link Crate} to the set
     * that store all crates
     * @param crate to add
     * @see ICrateService#getCrates()
     */
    void addCrate(Crate crate);

    /**
     * Remove a {@link Crate} from the set
     * that store all crates
     * @param name of the crate to remove
     * @see ICrateService#getCrates()
     */
    void removeCrate(String name);

    /**
     * Open a {@link Crate} to a player
     * @param crate to open
     * @param player that open the crate
     * @param animation to display to the player
     */
    void open(Crate crate, Player player, Animation animation);

    /**
     * Create a new {@link Crate}
     * @param name of the crate
     * @param item that represents the crate
     */
    void create(String name, ItemStack item);

    /**
     * Delete a {@link Crate}
     * @param name of the crate
     */
    void delete(String name);

    /**
     * Replace of insert new data of a {@link Crate}
     * @param crate to save
     */
    void save(Crate crate);

    /**
     * Check if a player is editing the
     * chance of a {@link CratePrize}
     * @param player to find
     * @return true if is editing
     */
    boolean isEditing(Player player);

    /**
     * Save temporary a player that is editing
     * a {@link CratePrize} and the prize that
     * is editing
     * @param player editor
     * @param prize that is editing
     */
    void addEditingUser(Player player, CratePrize prize);

    /**
     * Remove a player from the temporary editing map
     * @param player that was editing
     * @return the prize that was editing
     */
    CratePrize removeEditingUser(Player player);

}
