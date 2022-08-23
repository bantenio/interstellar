package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.AuthenticationMechanism;
import com.mongodb.MongoCredential;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.AuthenticationMechanism.*;

public class CredentialListParser {

    private final List<MongoCredential> credentials;

    public CredentialListParser(MongoClientProperties mongoClientProperties) {
        String username = mongoClientProperties.getUsername();
        // AuthMechanism
        AuthenticationMechanism mechanism = null;
        String authMechanism = mongoClientProperties.getAuthMechanism();
        if (authMechanism != null) {
            mechanism = getAuthenticationMechanism(authMechanism);
        }
        credentials = new ArrayList<>();
        if (username == null) {
            if (mechanism == MONGODB_X509) {
                credentials.add(MongoCredential.createMongoX509Credential());
            }
        } else {
            String passwd = mongoClientProperties.getPassword();
            char[] password = (passwd == null) ? null : passwd.toCharArray();
            // See https://github.com/vert-x3/vertx-mongo-client/issues/46 - 'admin' as default is a security
            // concern, use  the 'db_name' if none is set.
            String authSource = StringUtils.defaultIfBlank(mongoClientProperties.getAuthSource(), mongoClientProperties.getDbName());

            // MongoCredential
            String gssapiServiceName = mongoClientProperties.getGssapiServiceName();
            MongoCredential credential;
            if (mechanism == GSSAPI) {
                credential = MongoCredential.createGSSAPICredential(username);
                credential = getMongoCredential(gssapiServiceName, credential);
            } else if (mechanism == PLAIN) {
                credential = MongoCredential.createPlainCredential(username, authSource, password);
            } else if (mechanism == MONGODB_X509) {
                credential = MongoCredential.createMongoX509Credential(username);
            } else if (mechanism == SCRAM_SHA_1) {
                credential = MongoCredential.createScramSha1Credential(username, authSource, password);
            } else if (mechanism == SCRAM_SHA_256) {
                credential = MongoCredential.createScramSha256Credential(username, authSource, password);
            } else if (mechanism == null) {
                credential = MongoCredential.createCredential(username, authSource, password);
            } else {
                throw new IllegalArgumentException("Unsupported authentication mechanism " + mechanism);
            }

            credentials.add(credential);
        }
    }

    private MongoCredential getMongoCredential(String gssapiServiceName, MongoCredential credential) {
        if (gssapiServiceName != null) {
            credential = credential.withMechanismProperty("SERVICE_NAME", gssapiServiceName);
        }
        return credential;
    }

    private AuthenticationMechanism getAuthenticationMechanism(String authMechanism) {
        AuthenticationMechanism mechanism;
        try {
            mechanism = AuthenticationMechanism.fromMechanismName(authMechanism);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid authMechanism '" + authMechanism + "'");
        }
        return mechanism;
    }

    public List<MongoCredential> credentials() {
        return credentials;
    }

}
