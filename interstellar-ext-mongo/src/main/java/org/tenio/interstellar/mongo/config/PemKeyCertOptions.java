package org.tenio.interstellar.mongo.config;

import cn.hutool.core.io.FileUtil;
import org.tenio.interstellar.buffer.Buffer;
import org.tenio.interstellar.mongo.config.parser.KeyStoreHelper;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509KeyManager;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class PemKeyCertOptions {
    private KeyStoreHelper helper;
    private List<String> keyPaths;
    private List<Buffer> keyValues;
    private List<String> certPaths;
    private List<Buffer> certValues;

    /**
     * Default constructor
     */
    public PemKeyCertOptions() {
        super();
        init();
    }

    private void init() {
        keyPaths = new ArrayList<>();
        keyValues = new ArrayList<>();
        certPaths = new ArrayList<>();
        certValues = new ArrayList<>();
    }

    /**
     * Copy constructor
     *
     * @param other  the options to copy
     */
    public PemKeyCertOptions(PemKeyCertOptions other) {
        super();
        this.keyPaths = other.keyPaths != null ? new ArrayList<>(other.keyPaths) : new ArrayList<>();
        this.keyValues = other.keyValues != null ? new ArrayList<>(other.keyValues) : new ArrayList<>();
        this.certPaths = other.certPaths != null ? new ArrayList<>(other.certPaths) : new ArrayList<>();
        this.certValues = other.certValues != null ? new ArrayList<>(other.certValues) : new ArrayList<>();
    }

    /**
     * Get the path to the first key file
     *
     * @return the path to the key file
     */
    public String getKeyPath() {
        return keyPaths.isEmpty() ? null : keyPaths.get(0);
    }

    /**
     * Set the path of the first key file, replacing the keys paths
     *
     * @param keyPath  the path to the first key file
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions setKeyPath(String keyPath) {
        keyPaths.clear();
        if (keyPath != null) {
            keyPaths.add(keyPath);
        }
        return this;
    }

    /**
     * Get all the paths to the key files
     *
     * @return the paths to the keys files
     */
    public List<String> getKeyPaths() {
        return keyPaths;
    }

    /**
     * Set all the paths to the keys files
     *
     * @param keyPaths  the paths to the keys files
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions setKeyPaths(List<String> keyPaths) {
        this.keyPaths.clear();
        this.keyPaths.addAll(keyPaths);
        return this;
    }

    /**
     * Add a path to a key file
     *
     * @param keyPath  the path to the key file
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions addKeyPath(String keyPath) {
        Objects.requireNonNull(keyPath, "keyPath must not be null");
        keyPaths.add(keyPath);
        return this;
    }

    /**
     * Get the first key as a buffer
     *
     * @return  the first key as a buffer
     */
    public Buffer getKeyValue() {
        return keyValues.isEmpty() ? null : keyValues.get(0);
    }

    /**
     * Set the first key a a buffer, replacing the previous keys buffers
     *
     * @param keyValue  key as a buffer
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions setKeyValue(Buffer keyValue) {
        keyValues.clear();
        if (keyValue != null) {
            keyValues.add(keyValue);
        }
        return this;
    }

    /**
     * Get all the keys as a list of buffer
     *
     * @return  keys as a list of buffers
     */
    public List<Buffer> getKeyValues() {
        return keyValues;
    }

    /**
     * Set all the keys as a list of buffer
     *
     * @param keyValues  the keys as a list of buffer
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions setKeyValues(List<Buffer> keyValues) {
        this.keyValues.clear();
        this.keyValues.addAll(keyValues);
        return this;
    }

    /**
     * Add a key as a buffer
     *
     * @param keyValue the key to add
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions addKeyValue(Buffer keyValue) {
        Objects.requireNonNull(keyValue, "Null keyValue");
        keyValues.add(keyValue);
        return this;
    }

    /**
     * Get the path to the first certificate file
     *
     * @return  the path to the certificate file
     */
    public String getCertPath() {
        return certPaths.isEmpty() ? null : certPaths.get(0);
    }

    /**
     * Set the path of the first certificate, replacing the previous certificates paths
     *
     * @param certPath  the path to the certificate
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions setCertPath(String certPath) {
        certPaths.clear();
        if (certPath != null) {
            certPaths.add(certPath);
        }
        return this;
    }

    /**
     * Get all the paths to the certificates files
     *
     * @return the paths to the certificates files
     */
    public List<String> getCertPaths() {
        return certPaths;
    }

    /**
     * Set all the paths to the certificates files
     *
     * @param certPaths  the paths to the certificates files
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions setCertPaths(List<String> certPaths) {
        this.certPaths.clear();
        this.certPaths.addAll(certPaths);
        return this;
    }

    /**
     * Add a path to a certificate file
     *
     * @param certPath  the path to the certificate file
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions addCertPath(String certPath) {
        Objects.requireNonNull(certPath, "Null certPath");
        certPaths.add(certPath);
        return this;
    }

    /**
     * Get the first certificate as a buffer
     *
     * @return  the first certificate as a buffer
     */
    public Buffer getCertValue() {
        return certValues.isEmpty() ? null : certValues.get(0);
    }

    /**
     * Set the first certificate as a buffer, replacing the previous certificates buffers
     *
     * @param certValue  the first certificate as a buffer
     * @return a reference to this, so the API can be used fluently
     */
    public PemKeyCertOptions setCertValue(Buffer certValue) {
        certValues.clear();
        if (certValue != null) {
            certValues.add(certValue);
        }
        return this;
    }

    /**
     *
     * TODO
     *
     * @return TODO
     */
    public List<Buffer> getCertValues() {
        return certValues;
    }

    /**
     *
     * TODO
     *
     * @param certValues TODO
     * @return TODO
     */
    public PemKeyCertOptions setCertValues(List<Buffer> certValues) {
        this.certValues.clear();
        this.certValues.addAll(certValues);
        return this;
    }

    /**
     * TODO
     *
     * @param certValue TODO
     * @return TODO
     */
    public PemKeyCertOptions addCertValue(Buffer certValue) {
        Objects.requireNonNull(certValue != null, "Null certValue");
        certValues.add(certValue);
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public PemKeyCertOptions copy() {
        return new PemKeyCertOptions(this);
    }

    KeyStoreHelper getHelper() throws Exception {
        if (helper == null) {
            List<Buffer> keys = new ArrayList<>();
            for (String keyPath : keyPaths) {
                String content = FileUtil.readString(new File(keyPath), StandardCharsets.UTF_8);
                keys.add(Buffer.buffer(content));
            }
            keys.addAll(keyValues);
            List<Buffer> certs = new ArrayList<>();
            for (String certPath : certPaths) {
                String content = FileUtil.readString(new File(certPath), StandardCharsets.UTF_8);
                certs.add(Buffer.buffer(content));
            }
            certs.addAll(certValues);
            helper = new KeyStoreHelper(KeyStoreHelper.loadKeyCert(keys, certs), KeyStoreHelper.DUMMY_PASSWORD);
        }
        return helper;
    }

    /**
     *
     * TODO
     *
     * @return TODO
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
    public KeyManagerFactory getKeyManagerFactory() throws Exception {
        KeyStoreHelper helper = getHelper();
        return helper != null ? helper.getKeyMgrFactory() : null;
    }

    /**
     * TODO
     *
     * @return TODO
     * @throws Exception TODO
     */
    public Function<String, X509KeyManager> keyManagerMapper() throws Exception {
        KeyStoreHelper helper = getHelper();
        return helper != null ? helper::getKeyMgr : null;
    }
}
