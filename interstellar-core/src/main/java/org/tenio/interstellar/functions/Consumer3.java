package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer3<P1, P2, P3> extends Serializable, Invoker {
    void accept(P1 p1, P2 p2, P3 p3);

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