package me.lolok.crates.database.connector;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;

public interface IMongoDBConnector {
    MongoClient getClient();

    MongoDatabase getDatabase();
}
