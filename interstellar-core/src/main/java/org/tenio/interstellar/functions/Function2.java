package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function2<P1, P2, R> extends Serializable {
    R apply(P1 p1, P2 p2);

    default <V> Function2<P1, P2, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (P1 p1, P2 p2) -> after.apply(apply(p1, p2));
    }
}
