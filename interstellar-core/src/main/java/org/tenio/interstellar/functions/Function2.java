package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function2<P1, P2, R> extends Serializable, Invoker {
    R apply(P1 p1, P2 p2);

    default <V> Function2<P1, P2, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2) -> after.apply(apply(p1, p2));
    }

    default Object invoke(Object[] args) {
        return apply((P1) args[0], (P2) args[1]);
    }

    default boolean hasResult() {
        return true;
    }
}