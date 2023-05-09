package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import org.tenio.interstellar.mongo.config.MongoClientProperties;

import java.util.Optional;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class ReadConcernLevelParser {
    private final ConnectionString connectionString;
    private final MongoClientProperties config;

    /**
     *
     * TODO
     *
     * @param connectionString TODO
     * @param config TODO
     */
    public ReadConcernLevelParser(ConnectionString connectionString, MongoClientProperties config) {
        this.connectionString = connectionString;
        this.config = config;
    }

    /**
     *
     * TODO
     *
     * @return TODO
     */
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
