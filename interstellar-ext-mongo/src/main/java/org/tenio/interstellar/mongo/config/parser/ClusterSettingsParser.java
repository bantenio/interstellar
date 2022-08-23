package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.ServerAddress;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterSettings;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ClusterSettingsParser {

    private final ClusterSettings settings;

    public ClusterSettingsParser(ConnectionString connectionString, MongoClientProperties config) {
        ClusterSettings.Builder settings = ClusterSettings.builder();

        MongoClientProperties.ClusterProperties clusterProperties = config.getCluster();
        // ConnectionString takes precedence
        if (connectionString != null) {
            settings.applyConnectionString(connectionString);
        } else {
            // hosts
            List<ServerAddress> hosts = parseHosts(config, clusterProperties);
            settings.hosts(hosts);

            // replica set / mode
            String replicaSet = clusterProperties.getReplicaSet();
            if (hosts.size() == 1 && replicaSet == null) {
                settings.mode(ClusterConnectionMode.SINGLE);
            } else {
                settings.mode(ClusterConnectionMode.MULTIPLE);
            }
            if (replicaSet != null) {
                settings.requiredReplicaSetName(replicaSet);
            }

            // serverSelectionTimeoutMS
            Long serverSelectionTimeoutMS = clusterProperties.getServerSelectionTimeoutMS();
            if (serverSelectionTimeoutMS != null) {
                settings.serverSelectionTimeout(serverSelectionTimeoutMS, MILLISECONDS);
            }
        }

        this.settings = settings.build();
    }

    public ClusterSettings settings() {
        return settings;
    }

    private static List<ServerAddress> parseHosts(MongoClientProperties config, MongoClientProperties.ClusterProperties clusterProperties) {
        List<ServerAddress> hosts = new ArrayList<>();
        List<MongoClientProperties.ServerAddress> jsonHosts = clusterProperties.getServerAddressList();
        if (jsonHosts != null) {
            jsonHosts.forEach(jsonHost -> {
                ServerAddress address = serverAddress(jsonHost);
                if (address != null) {
                    hosts.add(address);
                }
            });
        } else {
            // Support host / port properties and if not present use default ServerAddress (127.0.0.1:27017)
            ServerAddress address = serverAddress(new MongoClientProperties.ServerAddress()
                    .setHost(config.getHost())
                    .setPort(config.getPort()));
            hosts.add(address == null ? new ServerAddress() : address);
        }

        return hosts;
    }

    private static ServerAddress serverAddress(MongoClientProperties.ServerAddress json) {
        if (json == null) return null;

        String host = json.getHost();
        Integer port = json.getPort();
        if (host == null) {
            return null;
        } else {
            if (port == null) {
                return new ServerAddress(host);
            }
            return new ServerAddress(host, port);
        }
    }
}
