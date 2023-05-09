package org.tenio.interstellar.mongo.config.parser;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mongodb.ConnectionString;
import com.mongodb.connection.SslSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tenio.interstellar.mongo.config.MongoClientProperties;
import org.tenio.interstellar.mongo.config.PemKeyCertOptions;
import org.tenio.interstellar.mongo.config.PemTrustOptions;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.security.SecureRandom;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class SSLSettingsParser {
    private static final Logger log = LoggerFactory.getLogger(SSLSettingsParser.class);
    private final ConnectionString connectionString;
    private final MongoClientProperties config;

    /**
     *
     * TODO
     *
     * @param connectionString TODO
     * @param config TODO
     */
    public SSLSettingsParser(ConnectionString connectionString, MongoClientProperties config) {
        this.connectionString = connectionString;
        this.config = config;
    }

    /**
     *
     * TODO
     *
     * @return TODO
     */
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
        if (StrUtil.isNotBlank(ssl.getCaPath())) {
            pemTrustOptions.addCertPath(ssl.getCaPath());
        }
        if (StrUtil.isAllNotBlank(ssl.getKeyPath(), ssl.getCertPath())) {
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
            builder.invalidHostNameAllowed(ObjectUtil.defaultIfNull(config.getSsl().getSslInvalidHostNameAllowed(), false));
        }
    }
}
