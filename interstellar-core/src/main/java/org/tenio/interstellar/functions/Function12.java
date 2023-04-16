package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> extends Serializable {
    R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12);

    default <V> Function12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12) -> after.apply(apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }
}