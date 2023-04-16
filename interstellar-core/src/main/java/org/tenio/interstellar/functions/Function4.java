package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function4<P1, P2, P3, P4, R> extends Serializable {
    R apply(P1 p1, P2 p2, P3 p3, P4 p4);

    default <V> Function4<P1, P2, P3, P4, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (P1 p1, P2 p2, P3 p3, P4 p4) -> after.apply(apply(p1, p2, p3, p4));
    }
}
