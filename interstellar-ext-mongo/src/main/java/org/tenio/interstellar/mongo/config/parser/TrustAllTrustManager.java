package org.tenio.interstellar.mongo.config.parser;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class TrustAllTrustManager implements X509TrustManager {
    /**
     *
     * TODO
     *
     */
    public static TrustAllTrustManager INSTANCE = new TrustAllTrustManager();

    private TrustAllTrustManager() {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
