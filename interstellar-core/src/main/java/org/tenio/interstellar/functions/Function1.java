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
 * @param <P1> TODO
 * @param <R>  TODO
 */
@FunctionalInterface
public interface Function1<P1, R> extends Serializable, Invoker {
    /**
     *
     * TODO
     *
     * @param p1 TODO
     * @return TODO
     */
    R apply(P1 p1);

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     * @param <V> TODO
     */
    default <V> Function1<P1, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1) -> after.apply(apply(p1));
    }

    default Object invoke(Object[] args) {
        return apply((P1) args[0]);
    }

    default boolean hasResult() {
        return true;
    }
}