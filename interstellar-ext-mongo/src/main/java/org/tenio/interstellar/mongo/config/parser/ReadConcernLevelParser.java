package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;

import java.util.Optional;

public class ReadConcernLevelParser {
    private final ConnectionString connectionString;
    private final MongoClientProperties config;

    public ReadConcernLevelParser(ConnectionString connectionString, MongoClientProperties config) {
        this.connectionString = connectionString;
        this.config = config;
    }

    public Optional<ReadConcern> readConcern() {
        return tryToParseFromConnectionString().map(this::lift).orElseGet(this::tryToParseFromConfig);
    }

    private Optional<ReadConcern> lift(ReadConcern readConcern) {
        return Optional.ofNullable(readConcern);
    }

    private Optional<ReadConcern> tryToParseFromConnectionString() {
        return Optional.ofNullable(connectionString)
                .flatMap(cs -> Optional.ofNullable(cs.getReadConcern()));
    }

    private Optional<ReadConcern> tryToParseFromConfig() {
        return Optional.ofNullable(config)
                .flatMap(cfg -> Optional.ofNullable(cfg.getReadConcernLevel()))
                .map(ReadConcernLevel::fromString)
                .map(ReadConcern::new);
    }
}
