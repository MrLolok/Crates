package me.lolok.crates.database.connector;

import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.Getter;
import me.lolok.crates.configurations.files.DatabaseConfiguration;
import me.lolok.crates.database.builder.MongoSettingsBuilder;

public class MongoDBConnector implements IMongoDBConnector {
    @Getter private MongoClient client;
    @Getter private MongoDatabase database;

    public MongoDBConnector(DatabaseConfiguration configuration) {
        MongoClientSettings settings = getSettings(configuration);
        initialize(settings, configuration.getDatabase());
    }

    public MongoDBConnector(MongoClientSettings settings, String database) {
        initialize(settings, database);
    }

    private void initialize(MongoClientSettings settings, String database) {
        this.client = MongoClients.create(settings);
        this.database = client.getDatabase(database);
    }

    private MongoClientSettings getSettings(DatabaseConfiguration configuration) {
        return new MongoSettingsBuilder()
                .setHost(configuration.getHost())
                .setPort(configuration.getPort())
                .setDatabase(configuration.getDatabase())
                .setUsername(configuration.getUser())
                .setPassword(configuration.getPassword())
                .build();
    }
}
