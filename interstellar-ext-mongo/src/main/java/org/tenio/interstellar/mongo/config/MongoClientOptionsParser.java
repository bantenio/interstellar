package org.tenio.interstellar.mongo.config;

import cn.hutool.core.util.ObjectUtil;
import com.mongodb.*;
import com.mongodb.connection.*;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.tenio.interstellar.context.mongo.DataObjectCodec;
import org.tenio.interstellar.mongo.config.parser.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class MongoClientOptionsParser {
    private final MongoClientSettings settings;
    private final String database;

    /**
     *
     * TODO
     *
     * @param config TODO
     */
    public MongoClientOptionsParser(MongoClientProperties config) {
        Objects.requireNonNull(config);
        MongoClientSettings.Builder options = MongoClientSettings.builder();
        CodecProvider pojoCodecProvider = PojoCodecProvider
                .builder()
                .automatic(true)
                .register(config.getSupportPojoPackages().stream().toArray(String[]::new))
                .build();
        options.codecRegistry(CodecRegistries.fromRegistries(
                CodecRegistries.fromCodecs(new DataObjectCodec(ObjectUtil.defaultIfNull(config.getUseObjectId(), false))),
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider)));

        // All parsers should support connection_string first
        String cs = config.getConnectionString();
        ConnectionString connectionString = (cs == null) ? null : new ConnectionString(cs);
        String csDatabase = (connectionString != null) ? connectionString.getDatabase() : null;
        this.database = csDatabase != null ? csDatabase : config.getDbName();

        // ClusterSettings
        ClusterSettings clusterSettings = new ClusterSettingsParser(connectionString, config).settings();
        options.applyToClusterSettings(builder -> builder.applySettings(clusterSettings));

        // ConnectionPoolSettings
        ConnectionPoolSettings connectionPoolSettings = new ConnectionPoolSettingsParser(connectionString, config).settings();
        options.applyToConnectionPoolSettings(builder -> builder.applySettings(connectionPoolSettings));

        // Credentials
        // The previous mongo client supported credentials list but their new implementation supports only
        // one credentials. The deprecated code path resorts to using the last credentials if a list is passed
        // we are doing the same here.
        List<MongoCredential> credentials = new CredentialListParser(config).credentials();
        if (!credentials.isEmpty())
            options.credential(credentials.get(credentials.size() - 1));

        // SocketSettings
        SocketSettings socketSettings = new SocketSettingsParser(connectionString, config).settings();
        options.applyToSocketSettings(builder -> builder.applySettings(socketSettings));

        // Transport type
        new StreamTypeParser(config).streamFactory().ifPresent(options::streamFactoryFactory);

        // SSLSettings
        SslSettings sslSettings = new SSLSettingsParser(connectionString, config).settings();
        options.applyToSslSettings(builder -> builder.applySettings(sslSettings));

        // WriteConcern
        WriteConcern writeConcern = new WriteConcernParser(connectionString, config).writeConcern();
        if (writeConcern != null) {
            options.writeConcern(writeConcern);
        }

        // ReadConcern
        maybeReadConcern(connectionString, config).ifPresent(options::readConcern);

        // ReadPreference
        ReadPreference readPreference = new ReadPreferenceParser(connectionString, config).readPreference();
        if (readPreference != null) {
            options.readPreference(readPreference);
        }

        // ServerSettings
        ServerSettings serverSettings = new ServerSettingsParser(config).settings();
        options.applyToServerSettings(builder -> builder.applySettings(serverSettings));

        this.settings = options.build();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public MongoClientSettings settings() {
        return settings;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String database() {
        return database;
    }

    private Optional<ReadConcern> maybeReadConcern(ConnectionString connectionString, MongoClientProperties config) {
        return new ReadConcernLevelParser(connectionString, config).readConcern();
    }
}
