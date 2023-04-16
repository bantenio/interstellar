package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer2<P1, P2> extends Serializable {
    void accept(P1 p1, P2 p2);

    default Consumer2<P1, P2> andThen(Consumer2<? super P1, ? super P2> after) {
        Objects.requireNonNull(after);

        return (p1, p2) -> {
            accept(p1, p2);
            after.accept(p1, p2);
        };
    }
}
