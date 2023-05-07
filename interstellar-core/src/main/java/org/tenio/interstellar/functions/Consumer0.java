package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
@FunctionalInterface
public interface Consumer0 extends Serializable, Invoker {
    /**
     *
     * TODO
     *
     */
    void accept();

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     */
    default Consumer0 andThen(Consumer0 after) {
        Objects.requireNonNull(after);

        return () -> {
            accept();
            after.accept();
        };
    }

    default Object invoke(Object[] args) {
        accept();
        return null;
    }

    default boolean hasResult() {
        return false;
    }
}