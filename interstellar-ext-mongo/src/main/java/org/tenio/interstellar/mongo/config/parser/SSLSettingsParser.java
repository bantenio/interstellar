package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.connection.SslSettings;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;
import com.weshare.zoo.ext.mongo.config.PemKeyCertOptions;
import com.weshare.zoo.ext.mongo.config.PemTrustOptions;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

public class SSLSettingsParser {
    private static final Logger log = LoggerFactory.getLogger(SSLSettingsParser.class);
    private final ConnectionString connectionString;
    private final MongoClientProperties config;

    public SSLSettingsParser(ConnectionString connectionString, MongoClientProperties config) {
        this.connectionString = connectionString;
        this.config = config;
    }

    public SslSettings settings() {
        final SslSettings.Builder builder = SslSettings.builder();
        fromConnectionString(builder);
        fromConfiguration(builder);

        final SslSettings settings = builder.build();
        if (!settings.isEnabled()) {
            return settings;
        }
        MongoClientProperties.SSLProperties ssl = config.getSsl();
        final PemKeyCertOptions pemKeyCertOptions = new PemKeyCertOptions();
        final PemTrustOptions pemTrustOptions = new PemTrustOptions();
        if (StringUtils.isNoneBlank(ssl.getCaPath())) {
            pemTrustOptions.addCertPath(ssl.getCaPath());
        }
        if (!StringUtils.isAnyBlank(ssl.getKeyPath(), ssl.getCertPath())) {
            pemKeyCertOptions.addKeyPath(ssl.getKeyPath());
            pemKeyCertOptions.addCertPath(ssl.getCertPath());
        }
        try {
            final TrustManager[] tms;
            if (ssl.isTrustAll()) {
                log.warn("Mongo client has been set to trust ALL certificates, this can open you up to security issues. Make sure you know the risks.");
                tms = new TrustManager[]{TrustAllTrustManager.INSTANCE};
            } else if (!pemTrustOptions.getCertPaths().isEmpty()) {
                tms = pemTrustOptions.getTrustManagerFactory().getTrustManagers();
            } else {
                tms = null;
            }
            final SSLContext context = SSLContext.getInstance("TLS");
            KeyManager[] mgr = pemKeyCertOptions.getKeyManagerFactory().getKeyManagers();
            context.init(mgr, tms, new SecureRandom());
            builder.context(context);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
        return builder.build();
    }

    private void fromConnectionString(SslSettings.Builder builder) {
        if (connectionString != null) {
            builder.applyConnectionString(connectionString);
        }
    }

    private void fromConfiguration(SslSettings.Builder builder) {
        if (config.getSsl() != null) {
            builder.enabled(config.getSsl().isEnabled());
            builder.invalidHostNameAllowed(ObjectUtils.defaultIfNull(config.getSsl().getSslInvalidHostNameAllowed(), false));
        }
    }
}
