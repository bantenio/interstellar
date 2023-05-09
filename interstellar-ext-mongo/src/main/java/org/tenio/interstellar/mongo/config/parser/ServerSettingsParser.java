package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.connection.ServerSettings;
import org.tenio.interstellar.mongo.config.MongoClientProperties;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class ServerSettingsParser {
    private final ServerSettings settings;

    /**
     * TODO
     *
     * @param config TODO
     */
    public ServerSettingsParser(MongoClientProperties config) {
        ServerSettings.Builder settings = ServerSettings.builder();
        MongoClientProperties.ServerSettingsProperties serverSettings = config.getServerSettings();
        Long heartbeatFrequencyMS = serverSettings.getHeartbeatFrequencyMS();
        if (heartbeatFrequencyMS != null) {
            settings.heartbeatFrequency(heartbeatFrequencyMS, MILLISECONDS);
        }
        Long minHeartbeatFrequencyMS = serverSettings.getMinHeartbeatFrequencyMS();
        if (minHeartbeatFrequencyMS != null) {
            settings.minHeartbeatFrequency(minHeartbeatFrequencyMS, MILLISECONDS);
        }

        this.settings = settings.build();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public ServerSettings settings() {
        return settings;
    }
}
