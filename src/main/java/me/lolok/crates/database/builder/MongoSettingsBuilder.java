package me.lolok.crates.database.builder;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

public class MongoSettingsBuilder {
    private String host, database, username, password;
    private int port;

    /**
     * This method sets the host of this mongo settings
     * @param host of the settings
     * @return this MongoSettingsBuilder
     */
    public MongoSettingsBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * This method sets the database of this mongo settings
     * @param database of the settings
     * @return this MongoSettingsBuilder
     */
    public MongoSettingsBuilder setDatabase(String database) {
        this.database = database;
        return this;
    }

    /**
     * This method sets the username of this mongo settings
     * @param username of the settings
     * @return this MongoSettingsBuilder
     */
    public MongoSettingsBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * This method sets the password of this mongo settings
     * @param password of the settings
     * @return this MongoSettingsBuilder
     */
    public MongoSettingsBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * This method sets the port of this mongo settings
     * @param port of the settings
     * @return this MongoSettingsBuilder
     */
    public MongoSettingsBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public MongoClientSettings build() {
        ConnectionString connString = new ConnectionString(String.format("mongodb://%s:%s@%s:%d/%s?authSource=admin", username, password, host, port, database));
        return MongoClientSettings.builder().applyConnectionString(connString).retryWrites(true).build();
    }
}
