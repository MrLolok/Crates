package me.lolok.crates.database;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.Getter;
import lombok.SneakyThrows;
import me.lolok.crates.CratesPlugin;
import me.lolok.crates.configurations.files.DatabaseConfiguration;
import me.lolok.crates.database.connector.IMongoDBConnector;
import me.lolok.crates.database.connector.MongoDBConnector;
import me.lolok.crates.database.subscribers.ExistSubscriber;
import me.lolok.crates.database.subscribers.ObservableSubscriber;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MongoDBManager implements IMongoDBManager {
    private final Executor executor = (command) -> Bukkit.getScheduler().runTaskAsynchronously(CratesPlugin.getInstance(), command);

    @Getter
    private MongoClient client;

    @Getter
    private MongoDatabase database;

    public MongoDBManager(DatabaseConfiguration configuration) {
        IMongoDBConnector connector = new MongoDBConnector(configuration);
        initialize(connector);
    }

    private void initialize(IMongoDBConnector connector) {
        this.client = connector.getClient();
        this.database = connector.getDatabase();
    }

    @Override
    public <T> MongoCollection<T> getCollection(String name, Class<T> type) {
        return database.getCollection(name, type);
    }

    @Override
    public MongoCollection<Document> getCollection(String name) {
        return getCollection(name, Document.class);
    }

    @SneakyThrows
    @Override
    public CompletableFuture<Boolean> collectionExist(String name) {
        return CompletableFuture.supplyAsync(() -> {
            Publisher<String> publisher = database.listCollectionNames();

            ExistSubscriber subscriber = new ExistSubscriber(name);
            publisher.subscribe(subscriber);
            try {
                subscriber.await();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return subscriber.isExist();
        }, executor);
    }

    @SneakyThrows
    @Override
    public MongoCollection<Document> createCollectionAndGet(String name) {
        createCollection(name);
        return getCollection(name);
    }

    @Override
    public void createCollection(String name) {
        createCollection(name, new ObservableSubscriber<>());
    }

    @Override
    public void createCollection(String name, Subscriber<? super Void> subscriber) {
        collectionExist(name).whenComplete((exists, e) -> {
            if (e != null) {
                e.printStackTrace();
                return;
            }

            if (!exists)
                database.createCollection(name).subscribe(subscriber);
        });
    }

    @Override
    public void loadCollection(String name, Subscriber<? super Document> subscriber) {
        collectionExist(name).whenComplete((exists, e) -> {
            if (e != null) {
                e.printStackTrace();
                return;
            }

            System.out.println(name + " " + exists);
            MongoCollection<Document> collection = exists ? getCollection(name) : createCollectionAndGet(name);
            collection.find().subscribe(subscriber);
        });
    }
}
