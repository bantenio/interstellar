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
 * @param <P4> TODO
 * @param <P5> TODO
 * @param <P6> TODO
 * @param <R>  TODO
 */
@FunctionalInterface
public interface Function6<P1, P2, P3, P4, P5, P6, R> extends Serializable, Invoker {
    /**
     *
     * TODO
     *
     * @param p1 TODO
     * @param p2 TODO
     * @param p3 TODO
     * @param p4 TODO
     * @param p5 TODO
     * @param p6 TODO
     * @return TODO
     */
    R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     * @param <V> TODO
     */
    default <V> Function6<P1, P2, P3, P4, P5, P6, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6) -> after.apply(apply(p1, p2, p3, p4, p5, p6));
    }

    default Object invoke(Object[] args) {
        return apply((P1) args[0], (P2) args[1], (P3) args[2], (P4) args[3], (P5) args[4], (P6) args[5]);
    }


    default boolean hasResult() {
        return true;
    }
}