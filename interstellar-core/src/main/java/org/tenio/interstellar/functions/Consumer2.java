package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer2<P1, P2> extends Serializable, Invoker {
    void accept(P1 p1, P2 p2);

    default Consumer2<P1, P2> andThen(Consumer2<? super P1, ? super P2> after) {
        Objects.requireNonNull(after);

        return (p1, p2) -> {
            accept(p1, p2);
            after.accept(p1, p2);
        };
    }

    default Object invoke(Object[] args) {
        accept((P1) args[0], (P2) args[1]);
        return null;
    }

    default boolean hasResult() {
        return false;
    }
}