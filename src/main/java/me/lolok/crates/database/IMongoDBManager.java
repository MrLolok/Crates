package me.lolok.crates.database;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;
import org.reactivestreams.Subscriber;

import java.util.concurrent.CompletableFuture;

public interface IMongoDBManager {

    /**
     * Gets the {@link MongoDatabase} that the client has connected to
     * through {@link me.lolok.crates.database.connector.IMongoDBConnector}
     * @return the mongo database
     */
    MongoDatabase getDatabase();

    /**
     * Obtain from {@link IMongoDBManager#getDatabase()} a certain collection
     * @param name of the collection
     * @param type class that represents data stored into this collection
     * @param <T> type of objects stored into this collection
     * @return collection found
     */
    <T> MongoCollection<T> getCollection(String name, Class<T> type);

    /**
     * Obtain from {@link IMongoDBManager#getDatabase()} a certain collection.
     * The data stored into this collection are {@link Document}
     * @param name of the collection
     * @return collection found
     * @see IMongoDBManager#getCollection(String, Class)
     */
    MongoCollection<Document> getCollection(String name);

    /**
     * Check if a {@link MongoCollection} exists
     * @param name of the collection to find
     * @return true if the collection exists
     */
    CompletableFuture<Boolean> collectionExist(String name);

    /**
     * Creates a new {@link MongoCollection} and than
     * return the collection just created
     * @param name of the collection to create
     * @return the collection created
     * @see IMongoDBManager#createCollection(String)
     * @see IMongoDBManager#getCollection(String)
     */
    MongoCollection<Document> createCollectionAndGet(String name);

    /**
     * Creates a new {@link MongoCollection}
     * @param name of the collection to create
     * @see IMongoDBManager#createCollection(String, Subscriber)
     */
    void createCollection(String name);

    /**
     * Creates a new {@link MongoCollection} and than call a {@link Subscriber}
     * @param name of the collection to create
     * @param subscriber to call after the collection creation
     */
    void createCollection(String name, Subscriber<? super Void> subscriber);

    /**
     * Creates or simply get a collection and than call a {@link Subscriber}
     * to handle data stored into this collection
     * @param name of the collection
     * @param subscriber to call after loading the collection
     */
    void loadCollection(String name, Subscriber<? super Document> subscriber);

}
