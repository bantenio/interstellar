package org.tenio.interstellar.functions;

import java.util.Objects;

public interface Consumer3<P1, P2, P3> {
    void accept(P1 p1, P2 p2, P3 p3);

    default Consumer3<P1, P2, P3> andThen(Consumer3<? super P1, ? super P2, ? super P3> after) {
        Objects.requireNonNull(after);

        return (p1, p2, p3) -> {
            accept(p1, p2, p3);
            after.accept(p1, p2, p3);
        };
    }
}
