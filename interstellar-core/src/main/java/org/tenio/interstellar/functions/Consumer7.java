package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer7<P1, P2, P3, P4, P5, P6, P7> extends Serializable, Invoker {
    void accept(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);

    default Consumer7<P1, P2, P3, P4, P5, P6, P7> andThen(Consumer7<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7> after) {
        Objects.requireNonNull(after);

        return (p1, p2, p3, p4, p5, p6, p7) -> {
            accept(p1, p2, p3, p4, p5, p6, p7);
            after.accept(p1, p2, p3, p4, p5, p6, p7);
        };
    }

    default Object invoke(Object[] args) {
        accept((P1) args[0], (P2) args[1], (P3) args[2], (P4) args[3], (P5) args[4], (P6) args[5], (P7) args[6]);
        return null;
    }

    default boolean hasResult() {
        return false;
    }
}