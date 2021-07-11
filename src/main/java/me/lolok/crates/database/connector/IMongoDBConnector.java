package me.lolok.crates.database.connector;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;

public interface IMongoDBConnector {

    /**
     * Gets the {@link MongoClient} that has connected
     * to the Mongo database
     * @return client connected
     */
    MongoClient getClient();

    /**
     * Gets the {@link MongoDatabase} that the client
     * has connected to
     * @return database connected to
     */
    MongoDatabase getDatabase();

}
