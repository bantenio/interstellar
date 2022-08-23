package org.tenio.interstellar.mongo.config.parser;

import com.mongodb.ConnectionString;
import com.mongodb.WriteConcern;
import com.weshare.zoo.ext.mongo.config.MongoClientProperties;

import java.util.concurrent.TimeUnit;

public class WriteConcernParser {
    private final WriteConcern writeConcern;

    public WriteConcernParser(ConnectionString connectionString, MongoClientProperties config) {
        WriteConcern connStringWriteConcern = null;
        if (connectionString != null) {
            // WRITE_CONCERN_KEYS("safe", "w", "wtimeoutms", "fsync", "journal");
            connStringWriteConcern = connectionString.getWriteConcern();
        }
        if (connStringWriteConcern != null) {
            // Prefer connection string's write concern
            this.writeConcern = connStringWriteConcern;
        } else {
            // Allow convenient string value for writeConcern e.g. ACKNOWLEDGED, SAFE, MAJORITY, etc
            MongoClientProperties.WriteConcernProperties writeConcern = config.getWriteConcern();
            WriteConcern wc;
            String wcs = writeConcern.getWriteConcern();
            if (wcs != null) {
                wc = WriteConcern.valueOf(wcs);
                if (wc == null) throw new IllegalArgumentException("Invalid WriteConcern " + wcs);
            } else {
                // Support advanced write concern options. There's some inconsistencies between driver options
                // and mongo docs [http://bit.ly/10SYO6x] but we'll be consistent with the driver for this.
                Boolean safe = writeConcern.getSafe();
                Object w = writeConcern.getW();
                Integer wtimeout = writeConcern.getWtimeoutMS();
                Boolean j = writeConcern.getJ();
                if (j == null) {
                    j = writeConcern.getJournal();
                }

                if (w != null || wtimeout != null || (j != null && j)) {
                    if (w == null) {
                        wc = new WriteConcern(1);
                    } else {
                        wc = getWriteConcern(w);
                    }

                    if (wtimeout != null) {
                        wc = wc.withWTimeout(wtimeout, TimeUnit.MILLISECONDS);
                    }
                    if (j != null) {
                        wc = wc.withJournal(j);
                    }
                } else if (safe != null) {
                    wc = safe ? WriteConcern.ACKNOWLEDGED : WriteConcern.UNACKNOWLEDGED;
                } else {
                    wc = null; // no write concern
                }
            }

            this.writeConcern = wc;
        }
    }

    private WriteConcern getWriteConcern(Object w) {
        WriteConcern wc;
        if (w instanceof String) {
            wc = new WriteConcern((String) w);
        } else if (w instanceof Integer) {
            wc = new WriteConcern((int) w);
        } else {
            throw new IllegalArgumentException("Invalid type " + w.getClass() + " for w of WriteConcern");
        }
        return wc;
    }

    public WriteConcern writeConcern() {
        return writeConcern;
    }
}
