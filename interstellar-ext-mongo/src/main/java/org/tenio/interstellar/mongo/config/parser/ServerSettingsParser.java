package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.connection.ServerSettings;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ServerSettingsParser {
    private final ServerSettings settings;

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

    public ServerSettings settings() {
        return settings;
    }
}
