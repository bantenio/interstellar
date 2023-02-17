package org.tenio.interstellar.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function3<P1, P2, P3, R> {
    R apply(P1 p1, P2 p2, P3 p3);

    default <V> Function3<P1, P2, P3, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (P1 p1, P2 p2, P3 p3) -> after.apply(apply(p1, p2, p3));
    }
}
