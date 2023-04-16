package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function1<P1, R> extends Serializable {
    R apply(P1 p1);

    default <V> Function1<P1, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (P1 p1) -> after.apply(apply(p1));
    }
}
