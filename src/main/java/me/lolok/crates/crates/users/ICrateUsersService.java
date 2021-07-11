package me.lolok.crates.crates.users;

import me.lolok.crates.crates.Service;
import me.lolok.crates.crates.users.objects.CrateUser;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface ICrateUsersService extends Service {

    /**
     * Gets a set of {@link CrateUser} cached
     * @return a collection of users
     */
    Set<CrateUser> getUsers();

    /**
     * Gets a specific {@link CrateUser} searching
     * it based on his UUID
     * @param uuid of the user
     * @return user found
     */
    @Nullable CrateUser getUser(UUID uuid);

    /**
     * Add a {@link CrateUser} to cache
     * @param user to add
     */
    void addUser(CrateUser user);

    /**
     * Called when a player open a {@link me.lolok.crates.crates.crate.objects.Crate}
     * It increment the crates opened today,
     * update the last opened time and save
     * the user on mongo
     * @param uuid of the user
     */
    void open(UUID uuid);

    /**
     * Reset all users amount of crates
     * opened today
     */
    void resetOpened();

}
