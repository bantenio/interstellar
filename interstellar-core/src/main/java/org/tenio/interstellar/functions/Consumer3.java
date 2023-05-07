package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 *
 * @param <P1> TODO
 * @param <P2> TODO
 * @param <P3> TODO
 */
@FunctionalInterface
public interface Consumer3<P1, P2, P3> extends Serializable, Invoker {
    /**
     *
     * TODO
     *
     * @param p1 TODO
     * @param p2 TODO
     * @param p3 TODO
     */
    void accept(P1 p1, P2 p2, P3 p3);

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     */
    default Consumer3<P1, P2, P3> andThen(Consumer3<? super P1, ? super P2, ? super P3> after) {
        Objects.requireNonNull(after);

        return (p1, p2, p3) -> {
            accept(p1, p2, p3);
            after.accept(p1, p2, p3);
        };
    }

    default Object invoke(Object[] args) {
        accept((P1) args[0], (P2) args[1], (P3) args[2]);
        return null;
    }

    default boolean hasResult() {
        return false;
    }
}