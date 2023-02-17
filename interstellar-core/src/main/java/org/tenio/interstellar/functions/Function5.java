package org.tenio.interstellar.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function5<P1, P2, P3, P4, P5, R> {
    R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);

    default <V> Function5<P1, P2, P3, P4, P5, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (P1 p1, P2 p2, P3 p3, P4 p4, P5 p5) -> after.apply(apply(p1, p2, p3, p4, p5));
    }
}
