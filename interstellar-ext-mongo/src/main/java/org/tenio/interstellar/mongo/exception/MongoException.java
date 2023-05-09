package org.tenio.interstellar.mongo.exception;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class MongoException extends RuntimeException {
    /**
     * TODO
     */
    public MongoException() {
    }

    /**
     * TODO
     *
     * @param message TODO
     */
    public MongoException(String message) {
        super(message);
    }

    /**
     * TODO
     *
     * @param message TODO
     * @param cause   TODO
     */
    public MongoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * TODO
     *
     * @param cause TODO
     */
    public MongoException(Throwable cause) {
        super(cause);
    }

    /**
     * TODO
     *
     * @param message            TODO
     * @param cause              TODO
     * @param enableSuppression  TODO
     * @param writableStackTrace TODO
     */
    public MongoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
