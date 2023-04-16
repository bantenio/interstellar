package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer6<P1, P2, P3, P4, P5, P6> extends Serializable {
    void accept(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);

    default Consumer6<P1, P2, P3, P4, P5, P6> andThen(Consumer6<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6> after) {
        Objects.requireNonNull(after);

        return (p1, p2, p3, p4, p5, p6) -> {
            accept(p1, p2, p3, p4, p5, p6);
            after.accept(p1, p2, p3, p4, p5, p6);
        };
    }
}