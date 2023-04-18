package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> extends Serializable, Invoker {
    R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11);

    default <V> Function11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11) -> after.apply(apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
    }

    default Object invoke(Object[] args) {
        return apply((P1) args[0], (P2) args[1], (P3) args[2], (P4) args[3], (P5) args[4], (P6) args[5], (P7) args[6], (P8) args[7], (P9) args[8], (P10) args[9], (P11) args[10]);
    }


    default boolean hasResult() {
        return true;
    }
}