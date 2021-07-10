package me.lolok.crates.database;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.reactivestreams.Subscriber;

import java.util.concurrent.CompletableFuture;

public interface IMongoDBManager {
    <T> MongoCollection<T> getCollection(String name, Class<T> type);

    MongoCollection<Document> getCollection(String name);

    CompletableFuture<Boolean> collectionExist(String name);

    MongoCollection<Document> createCollectionAndGet(String name);

    void createCollection(String name);

    void createCollection(String name, Subscriber<? super Void> subscriber);

    MongoClient getClient();

    MongoDatabase getDatabase();

    void loadCollection(String name, Subscriber<? super Document> subscriber);
}
