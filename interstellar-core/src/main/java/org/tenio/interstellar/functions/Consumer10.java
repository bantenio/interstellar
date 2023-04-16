package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10> extends Serializable {
    void accept(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10);

    default Consumer10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10> andThen(Consumer10<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9, ? super P10> after) {
        Objects.requireNonNull(after);

        return (p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) -> {
            accept(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
            after.accept(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
        };
    }
}