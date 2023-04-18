package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer4<P1, P2, P3, P4> extends Serializable, Invoker {
    void accept(P1 p1, P2 p2, P3 p3, P4 p4);

    default Consumer4<P1, P2, P3, P4> andThen(Consumer4<? super P1, ? super P2, ? super P3, ? super P4> after) {
        Objects.requireNonNull(after);

        return (p1, p2, p3, p4) -> {
            accept(p1, p2, p3, p4);
            after.accept(p1, p2, p3, p4);
        };
    }

    default Object invoke(Object[] args) {
        accept((P1) args[0], (P2) args[1], (P3) args[2], (P4) args[3]);
        return null;
    }

    default boolean hasResult() {
        return false;
    }
}