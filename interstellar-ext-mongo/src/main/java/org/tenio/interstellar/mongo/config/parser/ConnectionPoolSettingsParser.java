package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.connection.ConnectionPoolSettings;
import org.tenio.interstellar.mongo.config.MongoClientProperties;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class ConnectionPoolSettingsParser {
    private final ConnectionPoolSettings settings;

    /**
     *
     * TODO
     *
     * @param connectionString TODO
     * @param config TODO
     */
    public ConnectionPoolSettingsParser(ConnectionString connectionString, MongoClientProperties config) {
        ConnectionPoolSettings.Builder settings = ConnectionPoolSettings.builder();
        MongoClientProperties.ConnectionPoolProperties poolProperties = config.getPool();
        if (connectionString != null) {
            settings.applyConnectionString(connectionString);
        } else {
            Integer maxPoolSize = poolProperties.getMaxPoolSize();
            if (maxPoolSize != null) {
                settings.maxSize(maxPoolSize);
            }
            Integer minPoolSize = poolProperties.getMinPoolSize();
            if (minPoolSize != null) {
                settings.minSize(minPoolSize);
            }
            Long maxIdleTimeMS = poolProperties.getMaxIdleTimeMS();
            if (maxIdleTimeMS != null) {
                settings.maxConnectionIdleTime(maxIdleTimeMS, MILLISECONDS);
            }
            Long maxLifeTimeMS = poolProperties.getMaxLifeTimeMS();
            if (maxLifeTimeMS != null) {
                settings.maxConnectionLifeTime(maxLifeTimeMS, MILLISECONDS);
            }
            Long waitQueueTimeoutMS = poolProperties.getWaitQueueTimeoutMS();
            if (waitQueueTimeoutMS != null) {
                settings.maxWaitTime(waitQueueTimeoutMS, MILLISECONDS);
            }
            Long maintenanceInitialDelayMS = poolProperties.getMaintenanceInitialDelayMS();
            if (maintenanceInitialDelayMS != null) {
                settings.maintenanceInitialDelay(maintenanceInitialDelayMS, MILLISECONDS);
            }
            Long maintenanceFrequencyMS = poolProperties.getMaintenanceFrequencyMS();
            if (maintenanceFrequencyMS != null) {
                settings.maintenanceFrequency(maintenanceFrequencyMS, MILLISECONDS);
            }
        }

        this.settings = settings.build();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public ConnectionPoolSettings settings() {
        return settings;
    }
}
