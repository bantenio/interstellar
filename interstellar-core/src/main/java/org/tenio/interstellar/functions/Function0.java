package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 *
 * @param <R> TODO
 */
@FunctionalInterface
public interface Function0<R> extends Serializable, Invoker {
    /**
     *
     * TODO
     *
     * @return TODO
     */
    R apply();

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     * @param <V> TODO
     */
    default <V> Function0<V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return () -> after.apply(apply());
    }

    default Object invoke(Object[] args) {
        return apply();
    }

    default boolean hasResult() {
        return true;
    }
}