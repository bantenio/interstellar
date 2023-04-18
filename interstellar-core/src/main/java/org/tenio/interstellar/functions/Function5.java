package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function5<P1, P2, P3, P4, P5, R> extends Serializable, Invoker {
    R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);

    default <V> Function5<P1, P2, P3, P4, P5, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2, P3 p3, P4 p4, P5 p5) -> after.apply(apply(p1, p2, p3, p4, p5));
    }

    default Object invoke(Object[] args) {
        return apply((P1) args[0], (P2) args[1], (P3) args[2], (P4) args[3], (P5) args[4]);
    }


    default boolean hasResult() {
        return true;
    }
}