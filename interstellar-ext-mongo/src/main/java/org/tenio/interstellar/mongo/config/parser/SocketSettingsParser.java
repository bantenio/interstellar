package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.connection.SocketSettings;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SocketSettingsParser {
    private final SocketSettings settings;

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

    public SocketSettings settings() {
        return settings;
    }
}
