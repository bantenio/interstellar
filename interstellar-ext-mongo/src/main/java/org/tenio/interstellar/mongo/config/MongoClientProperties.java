package org.tenio.interstellar.mongo.config;

import java.util.List;

public class MongoClientProperties {

    private String username;

    private String password;

    private String authSource;

    private String dbName;

    private String host;

    private Integer port;

    private String connectionString;

    private ClusterProperties cluster = new ClusterProperties();

    private ConnectionPoolProperties pool = new ConnectionPoolProperties();

    private SocketProperties socket = new SocketProperties();

    private SSLProperties ssl = new SSLProperties();

    private WriteConcernProperties writeConcern = new WriteConcernProperties();

    private ReadPreferenceProperties readPreference = new ReadPreferenceProperties();

    private ServerSettingsProperties serverSettings = new ServerSettingsProperties();

    private String authMechanism;

    private String gssapiServiceName;

    private String streamType;

    private String readConcernLevel;

    private Boolean useObjectId;

    public String getUsername() {
        return username;
    }

    public MongoClientProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MongoClientProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAuthSource() {
        return authSource;
    }

    public MongoClientProperties setAuthSource(String authSource) {
        this.authSource = authSource;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public MongoClientProperties setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public String getHost() {
        return host;
    }

    public MongoClientProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public MongoClientProperties setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public MongoClientProperties setConnectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    public ClusterProperties getCluster() {
        return cluster;
    }

    public MongoClientProperties setCluster(ClusterProperties cluster) {
        this.cluster = cluster;
        return this;
    }

    public ConnectionPoolProperties getPool() {
        return pool;
    }

    public MongoClientProperties setPool(ConnectionPoolProperties pool) {
        this.pool = pool;
        return this;
    }

    public SocketProperties getSocket() {
        return socket;
    }

    public MongoClientProperties setSocket(SocketProperties socket) {
        this.socket = socket;
        return this;
    }

    public SSLProperties getSsl() {
        return ssl;
    }

    public MongoClientProperties setSsl(SSLProperties ssl) {
        this.ssl = ssl;
        return this;
    }

    public WriteConcernProperties getWriteConcern() {
        return writeConcern;
    }

    public MongoClientProperties setWriteConcern(WriteConcernProperties writeConcern) {
        this.writeConcern = writeConcern;
        return this;
    }

    public ReadPreferenceProperties getReadPreference() {
        return readPreference;
    }

    public MongoClientProperties setReadPreference(ReadPreferenceProperties readPreference) {
        this.readPreference = readPreference;
        return this;
    }

    public ServerSettingsProperties getServerSettings() {
        return serverSettings;
    }

    public MongoClientProperties setServerSettings(ServerSettingsProperties serverSettings) {
        this.serverSettings = serverSettings;
        return this;
    }

    public String getAuthMechanism() {
        return authMechanism;
    }

    public MongoClientProperties setAuthMechanism(String authMechanism) {
        this.authMechanism = authMechanism;
        return this;
    }

    public String getGssapiServiceName() {
        return gssapiServiceName;
    }

    public MongoClientProperties setGssapiServiceName(String gssapiServiceName) {
        this.gssapiServiceName = gssapiServiceName;
        return this;
    }

    public String getStreamType() {
        return streamType;
    }

    public MongoClientProperties setStreamType(String streamType) {
        this.streamType = streamType;
        return this;
    }

    public String getReadConcernLevel() {
        return readConcernLevel;
    }

    public MongoClientProperties setReadConcernLevel(String readConcernLevel) {
        this.readConcernLevel = readConcernLevel;
        return this;
    }

    public Boolean getUseObjectId() {
        return useObjectId;
    }

    public MongoClientProperties setUseObjectId(Boolean useObjectId) {
        this.useObjectId = useObjectId;
        return this;
    }

    @Override
    public String toString() {
        return "MongoClientProperties{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authSource='" + authSource + '\'' +
                ", dbName='" + dbName + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", connectionString='" + connectionString + '\'' +
                ", cluster=" + cluster +
                ", pool=" + pool +
                ", socket=" + socket +
                ", ssl=" + ssl +
                ", writeConcern=" + writeConcern +
                ", readPreference=" + readPreference +
                ", serverSettings=" + serverSettings +
                ", authMechanism='" + authMechanism + '\'' +
                ", gssapiServiceName='" + gssapiServiceName + '\'' +
                ", streamType='" + streamType + '\'' +
                ", readConcernLevel='" + readConcernLevel + '\'' +
                ", useObjectId=" + useObjectId +
                '}';
    }

    public static class ClusterProperties {
        private String replicaSet;

        private Long serverSelectionTimeoutMS;

        private List<ServerAddress> serverAddressList;

        public String getReplicaSet() {
            return replicaSet;
        }

        public ClusterProperties setReplicaSet(String replicaSet) {
            this.replicaSet = replicaSet;
            return this;
        }

        public Long getServerSelectionTimeoutMS() {
            return serverSelectionTimeoutMS;
        }

        public ClusterProperties setServerSelectionTimeoutMS(Long serverSelectionTimeoutMS) {
            this.serverSelectionTimeoutMS = serverSelectionTimeoutMS;
            return this;
        }

        public List<ServerAddress> getServerAddressList() {
            return serverAddressList;
        }

        public ClusterProperties setServerAddressList(List<ServerAddress> serverAddressList) {
            this.serverAddressList = serverAddressList;
            return this;
        }

        @Override
        public String toString() {
            return "ClusterProperties{" +
                    "replicaSet='" + replicaSet + '\'' +
                    ", serverSelectionTimeoutMS=" + serverSelectionTimeoutMS +
                    ", serverAddressList=" + serverAddressList +
                    '}';
        }
    }

    public static class ServerAddress {
        private String host;

        private Integer port;

        public String getHost() {
            return host;
        }

        public ServerAddress setHost(String host) {
            this.host = host;
            return this;
        }

        public Integer getPort() {
            return port;
        }

        public ServerAddress setPort(Integer port) {
            this.port = port;
            return this;
        }

        @Override
        public String toString() {
            return "ServerAddress{" +
                    "host='" + host + '\'' +
                    ", port=" + port +
                    '}';
        }
    }

    public static class ConnectionPoolProperties {
        private Integer maxPoolSize;

        private Integer minPoolSize;

        private Long maxIdleTimeMS;

        private Long maxLifeTimeMS;

        private Long waitQueueTimeoutMS;

        private Long maintenanceInitialDelayMS;

        private Long maintenanceFrequencyMS;

        public Integer getMaxPoolSize() {
            return maxPoolSize;
        }

        public ConnectionPoolProperties setMaxPoolSize(Integer maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public Integer getMinPoolSize() {
            return minPoolSize;
        }

        public ConnectionPoolProperties setMinPoolSize(Integer minPoolSize) {
            this.minPoolSize = minPoolSize;
            return this;
        }

        public Long getMaxIdleTimeMS() {
            return maxIdleTimeMS;
        }

        public ConnectionPoolProperties setMaxIdleTimeMS(Long maxIdleTimeMS) {
            this.maxIdleTimeMS = maxIdleTimeMS;
            return this;
        }

        public Long getMaxLifeTimeMS() {
            return maxLifeTimeMS;
        }

        public ConnectionPoolProperties setMaxLifeTimeMS(Long maxLifeTimeMS) {
            this.maxLifeTimeMS = maxLifeTimeMS;
            return this;
        }

        public Long getWaitQueueTimeoutMS() {
            return waitQueueTimeoutMS;
        }

        public ConnectionPoolProperties setWaitQueueTimeoutMS(Long waitQueueTimeoutMS) {
            this.waitQueueTimeoutMS = waitQueueTimeoutMS;
            return this;
        }

        public Long getMaintenanceInitialDelayMS() {
            return maintenanceInitialDelayMS;
        }

        public ConnectionPoolProperties setMaintenanceInitialDelayMS(Long maintenanceInitialDelayMS) {
            this.maintenanceInitialDelayMS = maintenanceInitialDelayMS;
            return this;
        }

        public Long getMaintenanceFrequencyMS() {
            return maintenanceFrequencyMS;
        }

        public ConnectionPoolProperties setMaintenanceFrequencyMS(Long maintenanceFrequencyMS) {
            this.maintenanceFrequencyMS = maintenanceFrequencyMS;
            return this;
        }

        @Override
        public String toString() {
            return "ConnectionPoolProperties{" +
                    "maxPoolSize=" + maxPoolSize +
                    ", minPoolSize=" + minPoolSize +
                    ", maxIdleTimeMS=" + maxIdleTimeMS +
                    ", maxLifeTimeMS=" + maxLifeTimeMS +
                    ", waitQueueTimeoutMS=" + waitQueueTimeoutMS +
                    ", maintenanceInitialDelayMS=" + maintenanceInitialDelayMS +
                    ", maintenanceFrequencyMS=" + maintenanceFrequencyMS +
                    '}';
        }
    }

    public static class SocketProperties {
        private Integer connectTimeoutMS;

        private Integer socketTimeoutMS;
        private Integer receiveBufferSize;
        private Integer sendBufferSize;

        public Integer getConnectTimeoutMS() {
            return connectTimeoutMS;
        }

        public SocketProperties setConnectTimeoutMS(Integer connectTimeoutMS) {
            this.connectTimeoutMS = connectTimeoutMS;
            return this;
        }

        public Integer getSocketTimeoutMS() {
            return socketTimeoutMS;
        }

        public SocketProperties setSocketTimeoutMS(Integer socketTimeoutMS) {
            this.socketTimeoutMS = socketTimeoutMS;
            return this;
        }

        public Integer getReceiveBufferSize() {
            return receiveBufferSize;
        }

        public SocketProperties setReceiveBufferSize(Integer receiveBufferSize) {
            this.receiveBufferSize = receiveBufferSize;
            return this;
        }

        public Integer getSendBufferSize() {
            return sendBufferSize;
        }

        public SocketProperties setSendBufferSize(Integer sendBufferSize) {
            this.sendBufferSize = sendBufferSize;
            return this;
        }

        @Override
        public String toString() {
            return "SocketProperties{" +
                    "connectTimeoutMS=" + connectTimeoutMS +
                    ", socketTimeoutMS=" + socketTimeoutMS +
                    ", receiveBufferSize=" + receiveBufferSize +
                    ", sendBufferSize=" + sendBufferSize +
                    '}';
        }
    }

    public static class SSLProperties {

        private Boolean enabled;

        private Boolean trustAll;

        private Boolean sslInvalidHostNameAllowed;
        private String caPath;
        private String keyPath;
        private String certPath;

        public boolean isEnabled() {
            return enabled;
        }

        public SSLProperties setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public boolean isTrustAll() {
            return trustAll;
        }

        public SSLProperties setTrustAll(boolean trustAll) {
            this.trustAll = trustAll;
            return this;
        }

        public Boolean getSslInvalidHostNameAllowed() {
            return sslInvalidHostNameAllowed;
        }

        public SSLProperties setSslInvalidHostNameAllowed(Boolean sslInvalidHostNameAllowed) {
            this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
            return this;
        }

        public String getCaPath() {
            return caPath;
        }

        public SSLProperties setCaPath(String caPath) {
            this.caPath = caPath;
            return this;
        }

        public String getKeyPath() {
            return keyPath;
        }

        public SSLProperties setKeyPath(String keyPath) {
            this.keyPath = keyPath;
            return this;
        }

        public String getCertPath() {
            return certPath;
        }

        public SSLProperties setCertPath(String certPath) {
            this.certPath = certPath;
            return this;
        }

        @Override
        public String toString() {
            return "SSLProperties{" +
                    "caPath='" + caPath + '\'' +
                    ", keyPath='" + keyPath + '\'' +
                    ", certPath='" + certPath + '\'' +
                    '}';
        }
    }

    public static class WriteConcernProperties {
        private String writeConcern;
        private Boolean safe;
        private Object w;
        private Integer wtimeoutMS;
        private Boolean j;
        private Boolean journal;

        public String getWriteConcern() {
            return writeConcern;
        }

        public WriteConcernProperties setWriteConcern(String writeConcern) {
            this.writeConcern = writeConcern;
            return this;
        }

        public Boolean getSafe() {
            return safe;
        }

        public WriteConcernProperties setSafe(Boolean safe) {
            this.safe = safe;
            return this;
        }

        public Object getW() {
            return w;
        }

        public WriteConcernProperties setW(Object w) {
            this.w = w;
            return this;
        }

        public Integer getWtimeoutMS() {
            return wtimeoutMS;
        }

        public WriteConcernProperties setWtimeoutMS(Integer wtimeoutMS) {
            this.wtimeoutMS = wtimeoutMS;
            return this;
        }

        public Boolean getJ() {
            return j;
        }

        public WriteConcernProperties setJ(Boolean j) {
            this.j = j;
            return this;
        }

        public Boolean getJournal() {
            return journal;
        }

        public WriteConcernProperties setJournal(Boolean journal) {
            this.journal = journal;
            return this;
        }

        @Override
        public String toString() {
            return "WriteConcernProperties{" +
                    "writeConcern='" + writeConcern + '\'' +
                    ", safe=" + safe +
                    ", w=" + w +
                    ", wtimeoutMS=" + wtimeoutMS +
                    ", j=" + j +
                    ", journal=" + journal +
                    '}';
        }
    }

    public static class ReadPreferenceProperties {
        private String readPreference;
        private List<String> readPreferenceTags;

        public String getReadPreference() {
            return readPreference;
        }

        public ReadPreferenceProperties setReadPreference(String readPreference) {
            this.readPreference = readPreference;
            return this;
        }

        public List<String> getReadPreferenceTags() {
            return readPreferenceTags;
        }

        public ReadPreferenceProperties setReadPreferenceTags(List<String> readPreferenceTags) {
            this.readPreferenceTags = readPreferenceTags;
            return this;
        }

        @Override
        public String toString() {
            return "ReadPreferenceProperties{" +
                    "readPreference='" + readPreference + '\'' +
                    ", readPreferenceTags=" + readPreferenceTags +
                    '}';
        }
    }

    public static class ServerSettingsProperties {
        private Long heartbeatFrequencyMS;
        private Long minHeartbeatFrequencyMS;

        public Long getHeartbeatFrequencyMS() {
            return heartbeatFrequencyMS;
        }

        public ServerSettingsProperties setHeartbeatFrequencyMS(Long heartbeatFrequencyMS) {
            this.heartbeatFrequencyMS = heartbeatFrequencyMS;
            return this;
        }

        public Long getMinHeartbeatFrequencyMS() {
            return minHeartbeatFrequencyMS;
        }

        public ServerSettingsProperties setMinHeartbeatFrequencyMS(Long minHeartbeatFrequencyMS) {
            this.minHeartbeatFrequencyMS = minHeartbeatFrequencyMS;
            return this;
        }

        @Override
        public String toString() {
            return "ServerSettingsProperties{" +
                    "heartbeatFrequencyMS=" + heartbeatFrequencyMS +
                    ", minHeartbeatFrequencyMS=" + minHeartbeatFrequencyMS +
                    '}';
        }
    }
}
