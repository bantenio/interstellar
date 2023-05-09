package org.tenio.interstellar.mongo.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.tenio.interstellar.buffer.Buffer;
import org.tenio.interstellar.mongo.config.parser.KeyStoreHelper;
import org.tenio.interstellar.mongo.exception.MongoException;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class PemTrustOptions {

    private KeyStoreHelper helper;
    private ArrayList<String> certPaths;
    private ArrayList<Buffer> certValues;

    /**
     * Default constructor
     */
    public PemTrustOptions() {
        super();
        this.certPaths = new ArrayList<>();
        this.certValues = new ArrayList<>();
    }

    /**
     * Copy constructor
     *
     * @param other the options to copy
     */
    public PemTrustOptions(PemTrustOptions other) {
        super();
        this.certPaths = new ArrayList<>(other.getCertPaths());
        this.certValues = new ArrayList<>(other.getCertValues());
    }

    /**
     * @return the certificate paths used to locate certificates
     */
    public List<String> getCertPaths() {
        return certPaths;
    }

    /**
     * Add a certificate path
     *
     * @param certPath the path to add
     * @return a reference to this, so the API can be used fluently
     * @throws NullPointerException TODO
     */
    public PemTrustOptions addCertPath(String certPath) throws NullPointerException {
        Objects.requireNonNull(certPath, "No null certificate accepted");
        if (StrUtil.isBlank(certPath)) {
            throw new NullPointerException("No empty certificate path accepted");
        }
        certPaths.add(certPath);
        return this;
    }

    /**
     * @return the certificate values
     */
    public List<Buffer> getCertValues() {
        return certValues;
    }

    /**
     * Add a certificate value
     *
     * @param certValue the value to add
     * @return a reference to this, so the API can be used fluently
     * @throws NullPointerException TODO
     */
    public PemTrustOptions addCertValue(Buffer certValue) throws NullPointerException {
        Objects.requireNonNull(certValue, "No null certificate accepted");
        certValues.add(certValue);
        return this;
    }

    /**
     * Load and return a Java keystore.
     *
     * @return the {@code KeyStore}
     * @throws Exception TODO
     */
    public KeyStore loadKeyStore() throws Exception {
        KeyStoreHelper helper = getHelper();
        return helper != null ? helper.store() : null;
    }

    /**
     * TODO
     *
     * @return TODO
     * @throws Exception TODO
     */
    public TrustManagerFactory getTrustManagerFactory() throws Exception {
        KeyStoreHelper helper = getHelper();
        return helper != null ? helper.getTrustMgrFactory() : null;
    }

    /**
     * TODO
     *
     * @return TODO
     * @throws Exception TODO
     */
    public Function<String, TrustManager[]> trustManagerMapper() throws Exception {
        KeyStoreHelper helper = getHelper();
        return helper != null ? helper::getTrustMgr : null;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public PemTrustOptions copy() {
        return new PemTrustOptions(this);
    }

    KeyStoreHelper getHelper() throws Exception {
        if (helper == null) {
            Stream<Buffer> certValues = certPaths.
                    stream().
                    map(path -> (new File(path))).
                    map(file -> {
                        try {
                            return Buffer.buffer(FileUtil.readString(file, StandardCharsets.UTF_8));
                        } catch (Exception e) {
                            throw new MongoException(e);
                        }
                    });
            certValues = Stream.concat(certValues, this.certValues.stream());
            helper = new KeyStoreHelper(KeyStoreHelper.loadCA(certValues), null);
        }
        return helper;
    }
}
