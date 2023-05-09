package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.connection.SocketSettings;
import org.tenio.interstellar.mongo.config.MongoClientProperties;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class SocketSettingsParser {
    private final SocketSettings settings;

    /**
     *
     * TODO
     *
     * @param connectionString TODO
     * @param config TODO
     */
    public SocketSettingsParser(ConnectionString connectionString, MongoClientProperties config) {
        SocketSettings.Builder settings = SocketSettings.builder();
        MongoClientProperties.SocketProperties socketProperties = config.getSocket();
        if (connectionString != null) {
            settings.applyConnectionString(connectionString);
        } else {
            Integer connectTimeoutMS = socketProperties.getConnectTimeoutMS();
            if (connectTimeoutMS != null) {
                settings.connectTimeout(connectTimeoutMS, MILLISECONDS);
            }
            Integer socketTimeoutMS = socketProperties.getSocketTimeoutMS();
            if (socketTimeoutMS != null) {
                settings.readTimeout(socketTimeoutMS, MILLISECONDS);
            }
            Integer receiveBufferSize = socketProperties.getReceiveBufferSize();
            if (receiveBufferSize != null) {
                settings.receiveBufferSize(receiveBufferSize);
            }
            Integer sendBufferSize = socketProperties.getSendBufferSize();
            if (sendBufferSize != null) {
                settings.sendBufferSize(sendBufferSize);
            }
        }

        this.settings = settings.build();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public SocketSettings settings() {
        return settings;
    }
}
