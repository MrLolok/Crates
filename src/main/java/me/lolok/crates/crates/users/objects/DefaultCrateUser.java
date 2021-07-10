package me.lolok.crates.crates.users.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@RequiredArgsConstructor
public class DefaultCrateUser implements CrateUser {
    @Getter
    private final UUID uniqueId;

    @Getter @Setter
    protected int opened = 0;
    @Getter @Setter
    protected long lastOpen = 0;

    @Nullable
    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }
}
