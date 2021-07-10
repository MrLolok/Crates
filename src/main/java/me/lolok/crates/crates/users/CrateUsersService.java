package me.lolok.crates.crates.users;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.reactivestreams.client.MongoCollection;
import lombok.Getter;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.crates.users.objects.DefaultCrateUser;
import me.lolok.crates.crates.users.objects.CrateUser;
import me.lolok.crates.crates.users.tasks.UsersClearTask;
import me.lolok.crates.database.subscribers.ObservableSubscriber;
import me.lolok.crates.database.subscribers.impl.UsersLoadingSubscriber;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CrateUsersService implements ICrateUsersService {
    private final CratesPlugin plugin;

    @Getter
    private final Set<CrateUser> users = new HashSet<>();

    private int taskId;

    public CrateUsersService(CratesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {
        plugin.getMongoDBManager().loadCollection("users", new UsersLoadingSubscriber(this));
        taskId = new UsersClearTask(this).runTaskTimerAsynchronously(plugin, 0L, 20L).getTaskId();
    }

    @Override
    public void disable() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    @Nullable
    public CrateUser getUser(UUID uuid) {
        for (CrateUser user : users)
            if (user.getUniqueId() == uuid)
                return user;
        return null;
    }

    @Override
    public void addUser(CrateUser user) {
        users.add(user);
    }

    @Override
    public void open(UUID uuid) {
        CrateUser user = getUser(uuid);
        user = user != null ? user : new DefaultCrateUser(uuid);
        user.setOpened(user.getOpened() + 1);
        user.setLastOpen(System.currentTimeMillis());

        Document document = new Document();
        document.put("uuid", uuid.toString());
        document.put("opened", user.getOpened());
        document.put("lastOpen", user.getLastOpen());

        MongoCollection<Document> collection = plugin.getMongoDBManager().getCollection("users");
        collection.replaceOne(Filters.eq("uuid", user.getUniqueId().toString()), document, new ReplaceOptions().upsert(true)).subscribe(new ObservableSubscriber<>());
    }

    @Override
    public void clear() {
        users.clear();
        plugin.getMongoDBManager().getCollection("users").deleteMany(new Document()).subscribe(new ObservableSubscriber<>());
        Bukkit.getLogger().info("Users cache cleared");
    }
}
