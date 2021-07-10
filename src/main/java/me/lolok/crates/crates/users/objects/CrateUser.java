package me.lolok.crates.crates.users.objects;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface CrateUser {

    UUID getUniqueId();

    int getOpened();

    long getLastOpen();

    void setOpened(int opened);

    void setLastOpen(long lastOpen);

    @Nullable Player getBukkitPlayer();

}
