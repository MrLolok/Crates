package me.lolok.crates.crates.crate;

import me.lolok.crates.CratesPlugin;
import me.lolok.crates.crates.Service;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.users.ICrateUsersService;
import me.lolok.crates.items.objects.CrateItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public interface ICrateService extends Service {
    int MAX_PER_DAY = CratesPlugin.getInstance().getConfig().getInt("opening.max-per-day");
    int DELAY = CratesPlugin.getInstance().getConfig().getInt("opening.delay");

    ICrateUsersService getUsersService();

    Set<Crate> getCrates();

    @Nullable Crate getCrate(String name);

    void addCrate(Crate crate);

    void removeCrate(String name);

    void open(Crate crate, Player player);

    void create(String name, ItemStack item);

    void delete(String name);

    void save(Crate crate);

    boolean isEditing(Player player);

    void addEditingUser(Player player, CrateItem item);

    CrateItem removeEditingUser(Player player);
}
