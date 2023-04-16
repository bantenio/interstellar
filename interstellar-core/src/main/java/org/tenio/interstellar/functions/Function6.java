package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function6<P1, P2, P3, P4, P5, P6, R> extends Serializable {
    R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);

    default <V> Function6<P1, P2, P3, P4, P5, P6, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6) -> after.apply(apply(p1, p2, p3, p4, p5, p6));
    }
}