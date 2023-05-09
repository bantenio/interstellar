package org.tenio.interstellar.mongo.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
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

    private Set<String> supportPojoPackages = new HashSet<>();

    /**
     * TODO
     *
     * @return TODO
     */
    public String getUsername() {
        return username;
    }

    /**
     * TODO
     *
     * @param username TODO
     * @return TODO
     */
    public MongoClientProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getPassword() {
        return password;
    }

    /**
     * TODO
     *
     * @param password TODO
     * @return TODO
     */
    public MongoClientProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getAuthSource() {
        return authSource;
    }

    /**
     * TODO
     *
     * @param authSource TODO
     * @return TODO
     */
    public MongoClientProperties setAuthSource(String authSource) {
        this.authSource = authSource;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * TODO
     *
     * @param dbName TODO
     * @return TODO
     */
    public MongoClientProperties setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getHost() {
        return host;
    }

    /**
     * TODO
     *
     * @param host TODO
     * @return TODO
     */
    public MongoClientProperties setHost(String host) {
        this.host = host;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public Integer getPort() {
        return port;
    }

    /**
     * TODO
     *
     * @param port TODO
     * @return TODO
     */
    public MongoClientProperties setPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * TODO
     *
     * @param connectionString TODO
     * @return TODO
     */
    public MongoClientProperties setConnectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public ClusterProperties getCluster() {
        return cluster;
    }

    /**
     * TODO
     *
     * @param cluster TODO
     * @return TODO
     */
    public MongoClientProperties setCluster(ClusterProperties cluster) {
        this.cluster = cluster;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public ConnectionPoolProperties getPool() {
        return pool;
    }

    /**
     * TODO
     *
     * @param pool TODO
     * @return TODO
     */
    public MongoClientProperties setPool(ConnectionPoolProperties pool) {
        this.pool = pool;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public SocketProperties getSocket() {
        return socket;
    }

    /**
     * TODO
     *
     * @param socket TODO
     * @return TODO
     */
    public MongoClientProperties setSocket(SocketProperties socket) {
        this.socket = socket;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public SSLProperties getSsl() {
        return ssl;
    }

    /**
     * TODO
     *
     * @param ssl TODO
     * @return TODO
     */
    public MongoClientProperties setSsl(SSLProperties ssl) {
        this.ssl = ssl;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public WriteConcernProperties getWriteConcern() {
        return writeConcern;
    }

    /**
     * TODO
     *
     * @param writeConcern TODO
     * @return TODO
     */
    public MongoClientProperties setWriteConcern(WriteConcernProperties writeConcern) {
        this.writeConcern = writeConcern;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public ReadPreferenceProperties getReadPreference() {
        return readPreference;
    }

    /**
     * TODO
     *
     * @param readPreference TODO
     * @return TODO
     */
    public MongoClientProperties setReadPreference(ReadPreferenceProperties readPreference) {
        this.readPreference = readPreference;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public ServerSettingsProperties getServerSettings() {
        return serverSettings;
    }

    /**
     * TODO
     *
     * @param serverSettings TODO
     * @return TODO
     */
    public MongoClientProperties setServerSettings(ServerSettingsProperties serverSettings) {
        this.serverSettings = serverSettings;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getAuthMechanism() {
        return authMechanism;
    }

    /**
     * TODO
     *
     * @param authMechanism TODO
     * @return TODO
     */
    public MongoClientProperties setAuthMechanism(String authMechanism) {
        this.authMechanism = authMechanism;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getGssapiServiceName() {
        return gssapiServiceName;
    }

    /**
     * TODO
     *
     * @param gssapiServiceName TODO
     * @return TODO
     */
    public MongoClientProperties setGssapiServiceName(String gssapiServiceName) {
        this.gssapiServiceName = gssapiServiceName;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getStreamType() {
        return streamType;
    }

    /**
     * TODO
     *
     * @param streamType TODO
     * @return TODO
     */
    public MongoClientProperties setStreamType(String streamType) {
        this.streamType = streamType;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public String getReadConcernLevel() {
        return readConcernLevel;
    }

    /**
     * TODO
     *
     * @param readConcernLevel TODO
     * @return TODO
     */
    public MongoClientProperties setReadConcernLevel(String readConcernLevel) {
        this.readConcernLevel = readConcernLevel;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public Boolean getUseObjectId() {
        return useObjectId;
    }

    /**
     * TODO
     *
     * @param useObjectId TODO
     * @return TODO
     */
    public MongoClientProperties setUseObjectId(Boolean useObjectId) {
        this.useObjectId = useObjectId;
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public Set<String> getSupportPojoPackages() {
        return supportPojoPackages;
    }

    /**
     * TODO
     *
     * @param supportPojoPackages TODO
     * @return TODO
     */
    public MongoClientProperties setSupportPojoPackages(Set<String> supportPojoPackages) {
        this.supportPojoPackages = supportPojoPackages;
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
                ", supportPojoPackages=" + supportPojoPackages +
                '}';
    }

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class ClusterProperties {
        private String replicaSet;

        private Long serverSelectionTimeoutMS;

        private List<ServerAddress> serverAddressList;

        /**
         * TODO
         *
         * @return TODO
         */
        public String getReplicaSet() {
            return replicaSet;
        }

        /**
         * TODO
         *
         * @param replicaSet TODO
         * @return TODO
         */
        public ClusterProperties setReplicaSet(String replicaSet) {
            this.replicaSet = replicaSet;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getServerSelectionTimeoutMS() {
            return serverSelectionTimeoutMS;
        }

        /**
         * TODO
         *
         * @param serverSelectionTimeoutMS TODO
         * @return TODO
         */
        public ClusterProperties setServerSelectionTimeoutMS(Long serverSelectionTimeoutMS) {
            this.serverSelectionTimeoutMS = serverSelectionTimeoutMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public List<ServerAddress> getServerAddressList() {
            return serverAddressList;
        }

        /**
         * TODO
         *
         * @param serverAddressList TODO
         * @return TODO
         */
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

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class ServerAddress {
        private String host;

        private Integer port;

        /**
         * TODO
         *
         * @return TODO
         */
        public String getHost() {
            return host;
        }

        /**
         * TODO
         *
         * @param host TODO
         * @return TODO
         */
        public ServerAddress setHost(String host) {
            this.host = host;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getPort() {
            return port;
        }

        /**
         * TODO
         *
         * @param port TODO
         * @return TODO
         */
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

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class ConnectionPoolProperties {
        private Integer maxPoolSize;

        private Integer minPoolSize;

        private Long maxIdleTimeMS;

        private Long maxLifeTimeMS;

        private Long waitQueueTimeoutMS;

        private Long maintenanceInitialDelayMS;

        private Long maintenanceFrequencyMS;

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getMaxPoolSize() {
            return maxPoolSize;
        }

        /**
         * TODO
         *
         * @param maxPoolSize TODO
         * @return TODO
         */
        public ConnectionPoolProperties setMaxPoolSize(Integer maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getMinPoolSize() {
            return minPoolSize;
        }

        /**
         * TODO
         *
         * @param minPoolSize TODO
         * @return TODO
         */
        public ConnectionPoolProperties setMinPoolSize(Integer minPoolSize) {
            this.minPoolSize = minPoolSize;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getMaxIdleTimeMS() {
            return maxIdleTimeMS;
        }

        /**
         * TODO
         *
         * @param maxIdleTimeMS TODO
         * @return TODO
         */
        public ConnectionPoolProperties setMaxIdleTimeMS(Long maxIdleTimeMS) {
            this.maxIdleTimeMS = maxIdleTimeMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getMaxLifeTimeMS() {
            return maxLifeTimeMS;
        }

        /**
         * TODO
         *
         * @param maxLifeTimeMS TODO
         * @return TODO
         */
        public ConnectionPoolProperties setMaxLifeTimeMS(Long maxLifeTimeMS) {
            this.maxLifeTimeMS = maxLifeTimeMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getWaitQueueTimeoutMS() {
            return waitQueueTimeoutMS;
        }

        /**
         * TODO
         *
         * @param waitQueueTimeoutMS TODO
         * @return TODO
         */
        public ConnectionPoolProperties setWaitQueueTimeoutMS(Long waitQueueTimeoutMS) {
            this.waitQueueTimeoutMS = waitQueueTimeoutMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getMaintenanceInitialDelayMS() {
            return maintenanceInitialDelayMS;
        }

        /**
         * TODO
         *
         * @param maintenanceInitialDelayMS TODO
         * @return TODO
         */
        public ConnectionPoolProperties setMaintenanceInitialDelayMS(Long maintenanceInitialDelayMS) {
            this.maintenanceInitialDelayMS = maintenanceInitialDelayMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getMaintenanceFrequencyMS() {
            return maintenanceFrequencyMS;
        }

        /**
         * TODO
         *
         * @param maintenanceFrequencyMS TODO
         * @return TODO
         */
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

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class SocketProperties {
        private Integer connectTimeoutMS;

        private Integer socketTimeoutMS;
        private Integer receiveBufferSize;
        private Integer sendBufferSize;

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getConnectTimeoutMS() {
            return connectTimeoutMS;
        }

        /**
         * TODO
         *
         * @param connectTimeoutMS TODO
         * @return TODO
         */
        public SocketProperties setConnectTimeoutMS(Integer connectTimeoutMS) {
            this.connectTimeoutMS = connectTimeoutMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getSocketTimeoutMS() {
            return socketTimeoutMS;
        }

        /**
         * TODO
         *
         * @param socketTimeoutMS TODO
         * @return TODO
         */
        public SocketProperties setSocketTimeoutMS(Integer socketTimeoutMS) {
            this.socketTimeoutMS = socketTimeoutMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getReceiveBufferSize() {
            return receiveBufferSize;
        }

        /**
         * TODO
         *
         * @param receiveBufferSize TODO
         * @return TODO
         */
        public SocketProperties setReceiveBufferSize(Integer receiveBufferSize) {
            this.receiveBufferSize = receiveBufferSize;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getSendBufferSize() {
            return sendBufferSize;
        }

        /**
         * TODO
         *
         * @param sendBufferSize TODO
         * @return TODO
         */
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

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class SSLProperties {

        private boolean enabled;

        private boolean trustAll;

        private Boolean sslInvalidHostNameAllowed;
        private String caPath;
        private String keyPath;
        private String certPath;

        /**
         * TODO
         *
         * @return TODO
         */
        public boolean isEnabled() {
            return enabled;
        }

        /**
         * TODO
         *
         * @param enabled TODO
         * @return TODO
         */
        public SSLProperties setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public boolean isTrustAll() {
            return trustAll;
        }

        /**
         * TODO
         *
         * @param trustAll TODO
         * @return TODO
         */
        public SSLProperties setTrustAll(boolean trustAll) {
            this.trustAll = trustAll;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Boolean getSslInvalidHostNameAllowed() {
            return sslInvalidHostNameAllowed;
        }

        /**
         * TODO
         *
         * @param sslInvalidHostNameAllowed TODO
         * @return TODO
         */
        public SSLProperties setSslInvalidHostNameAllowed(Boolean sslInvalidHostNameAllowed) {
            this.sslInvalidHostNameAllowed = sslInvalidHostNameAllowed;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public String getCaPath() {
            return caPath;
        }

        /**
         * TODO
         *
         * @param caPath TODO
         * @return TODO
         */
        public SSLProperties setCaPath(String caPath) {
            this.caPath = caPath;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public String getKeyPath() {
            return keyPath;
        }

        /**
         * TODO
         *
         * @param keyPath TODO
         * @return TODO
         */
        public SSLProperties setKeyPath(String keyPath) {
            this.keyPath = keyPath;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public String getCertPath() {
            return certPath;
        }

        /**
         * TODO
         *
         * @param certPath TODO
         * @return TODO
         */
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

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class WriteConcernProperties {
        private String writeConcern;
        private Boolean safe;
        private Object w;
        private Integer wtimeoutMS;
        private Boolean j;
        private Boolean journal;

        /**
         * TODO
         *
         * @return TODO
         */
        public String getWriteConcern() {
            return writeConcern;
        }

        /**
         * TODO
         *
         * @param writeConcern TODO
         * @return TODO
         */
        public WriteConcernProperties setWriteConcern(String writeConcern) {
            this.writeConcern = writeConcern;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Boolean getSafe() {
            return safe;
        }

        /**
         * TODO
         *
         * @param safe TODO
         * @return TODO
         */
        public WriteConcernProperties setSafe(Boolean safe) {
            this.safe = safe;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Object getW() {
            return w;
        }

        /**
         * TODO
         *
         * @param w TODO
         * @return TODO
         */
        public WriteConcernProperties setW(Object w) {
            this.w = w;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Integer getWtimeoutMS() {
            return wtimeoutMS;
        }

        /**
         * TODO
         *
         * @param wtimeoutMS TODO
         * @return TODO
         */
        public WriteConcernProperties setWtimeoutMS(Integer wtimeoutMS) {
            this.wtimeoutMS = wtimeoutMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Boolean getJ() {
            return j;
        }

        /**
         * TODO
         *
         * @param j TODO
         * @return TODO
         */
        public WriteConcernProperties setJ(Boolean j) {
            this.j = j;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Boolean getJournal() {
            return journal;
        }

        /**
         * TODO
         *
         * @param journal TODO
         * @return TODO
         */
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

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class ReadPreferenceProperties {
        private String readPreference;
        private List<String> readPreferenceTags;

        /**
         * TODO
         *
         * @return TODO
         */
        public String getReadPreference() {
            return readPreference;
        }

        /**
         * TODO
         *
         * @param readPreference TODO
         * @return TODO
         */
        public ReadPreferenceProperties setReadPreference(String readPreference) {
            this.readPreference = readPreference;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public List<String> getReadPreferenceTags() {
            return readPreferenceTags;
        }

        /**
         * TODO
         *
         * @param readPreferenceTags TODO
         * @return TODO
         */
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

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    public static class ServerSettingsProperties {
        private Long heartbeatFrequencyMS;
        private Long minHeartbeatFrequencyMS;

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getHeartbeatFrequencyMS() {
            return heartbeatFrequencyMS;
        }

        /**
         * TODO
         *
         * @param heartbeatFrequencyMS TODO
         * @return TODO
         */
        public ServerSettingsProperties setHeartbeatFrequencyMS(Long heartbeatFrequencyMS) {
            this.heartbeatFrequencyMS = heartbeatFrequencyMS;
            return this;
        }

        /**
         * TODO
         *
         * @return TODO
         */
        public Long getMinHeartbeatFrequencyMS() {
            return minHeartbeatFrequencyMS;
        }

        /**
         * TODO
         *
         * @param minHeartbeatFrequencyMS TODO
         * @return TODO
         */
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
