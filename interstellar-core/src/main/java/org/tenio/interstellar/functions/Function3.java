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
 * @param <P2> TODO
 * @param <P3> TODO
 * @param <R>  TODO
 */
@FunctionalInterface
public interface Function3<P1, P2, P3, R> extends Serializable, Invoker {
    /**
     *
     * TODO
     *
     * @param p1 TODO
     * @param p2 TODO
     * @param p3 TODO
     * @return TODO
     */
    R apply(P1 p1, P2 p2, P3 p3);

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     * @param <V> TODO
     */
    default <V> Function3<P1, P2, P3, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2, P3 p3) -> after.apply(apply(p1, p2, p3));
    }

    default Object invoke(Object[] args) {
        return apply((P1) args[0], (P2) args[1], (P3) args[2]);
    }


    default boolean hasResult() {
        return true;
    }
}