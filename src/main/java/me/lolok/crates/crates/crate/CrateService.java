package me.lolok.crates.crates.crate;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.reactivestreams.client.MongoCollection;
import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.crates.crate.animations.Animation;
import me.lolok.crates.crates.crate.listeners.CrateChatListener;
import me.lolok.crates.crates.crate.listeners.CrateInteractListener;
import me.lolok.crates.crates.crate.objects.DefaultCrate;
import me.lolok.crates.crates.crate.objects.Crate;
import me.lolok.crates.crates.users.CrateUsersService;
import me.lolok.crates.crates.users.ICrateUsersService;
import me.lolok.crates.database.subscribers.ObservableSubscriber;
import me.lolok.crates.database.subscribers.impl.CratesLoadingSubscriber;
import me.lolok.crates.items.ItemSerializer;
import me.lolok.crates.items.nbt.WrappedCompound;
import me.lolok.crates.crates.crate.prizes.CratePrize;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CrateService implements ICrateService {
    private final CratesPlugin plugin;

    @Getter
    private final ICrateUsersService usersService;

    @Getter
    private final Set<Crate> crates = new HashSet<>();
    private final Map<UUID, CratePrize> editingUsers = new HashMap<>();

    private final CrateInteractListener interactListener = new CrateInteractListener(this);
    private final CrateChatListener chatListener = new CrateChatListener(this);

    public CrateService(CratesPlugin plugin) {
        this.plugin = plugin;
        this.usersService = new CrateUsersService(plugin);
    }

    @Override
    public void enable() {
        // Enable users service
        usersService.enable();

        // Load crates
        plugin.getMongoDBManager().loadCollection("crates", new CratesLoadingSubscriber(this));

        // Register crates listeners
        Bukkit.getServer().getPluginManager().registerEvents(interactListener, plugin);
        Bukkit.getServer().getPluginManager().registerEvents(chatListener, plugin);
    }

    @Override
    public void disable() {
        // Disable users service
        usersService.disable();

        // Unregister listeners
        HandlerList.unregisterAll(interactListener);
        HandlerList.unregisterAll(chatListener);
    }

    @Override
    @Nullable
    public Crate getCrate(String name) {
        for (Crate crate : crates)
            if (crate.getName().equalsIgnoreCase(name))
                return crate;
        return null;
    }

    @Override
    public void addCrate(Crate crate) {
        crates.add(crate);
    }

    @Override
    public void removeCrate(String name) {
        crates.removeIf(crate -> crate.getName().equalsIgnoreCase(name));
    }

    @Override
    public void open(Crate crate, Player player, Animation animation) {
        usersService.open(player.getUniqueId());
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        animation.start(player);
    }

    @Override
    public void create(String name, ItemStack item) {
        item.setAmount(1);
        item = WrappedCompound.of(item).setString("Crate", name).save(item);
        Crate crate = new DefaultCrate(this, name, item);
        addCrate(crate);
        save(crate);
    }

    @Override
    public void delete(String name) {
        removeCrate(name);
        MongoCollection<Document> collection = plugin.getMongoDBManager().getCollection("crates");
        collection.deleteOne(Filters.eq("name", name)).subscribe(new ObservableSubscriber<>());
    }

    @Override
    public void save(Crate crate) {
        List<Document> prizes = new ArrayList<>();
        crate.getPrizes().forEach(prize -> {
            Document doc = new Document();
            doc.put("item", ItemSerializer.toJSON(prize.getItem()));
            doc.put("chance", prize.getChance());
            prizes.add(doc);
        });

        Document document = new Document();
        document.put("name", crate.getName());
        document.put("item", ItemSerializer.toJSON(crate.getItem()));
        document.put("prizes", prizes);

        MongoCollection<Document> collection = plugin.getMongoDBManager().getCollection("crates");
        collection.replaceOne(Filters.eq("name", crate.getName()), document, new ReplaceOptions().upsert(true)).subscribe(new ObservableSubscriber<>());
    }

    @Override
    public boolean isEditing(Player player) {
        return editingUsers.containsKey(player.getUniqueId());
    }

    @Override
    public void addEditingUser(Player player, CratePrize prize) {
        editingUsers.putIfAbsent(player.getUniqueId(), prize);
    }

    @Override
    public CratePrize removeEditingUser(Player player) {
        return editingUsers.remove(player.getUniqueId());
    }
}
