package org.tenio.interstellar.mongo.exception;

public class MongoException extends RuntimeException {
    public MongoException() {
    }

    public MongoException(String message) {
        super(message);
    }

    public MongoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoException(Throwable cause) {
        super(cause);
    }

    public MongoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
