package me.lolok.crates.crates.users.objects;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface CrateUser {

    /**
     * Gets the UUID of this user
     * @return uuid of the user
     */
    UUID getUniqueId();

    /**
     * Gets the amount of crates opened today
     * @return crates opened amount
     */
    int getOpened();

    /**
     * Gets the last crate opened time
     * @return time of the last open
     */
    long getLastOpen();

    /**
     * Sets the amount of crates opened today
     * @param opened amount
     */
    void setOpened(int opened);

    /**
     * Sets the last crate opened time
     * @param lastOpen time
     */
    void setLastOpen(long lastOpen);

    /**
     * Obtain the Bukkit's player through {@link CrateUser#getUniqueId()}
     * @return player found
     */
    @Nullable Player getBukkitPlayer();

}
