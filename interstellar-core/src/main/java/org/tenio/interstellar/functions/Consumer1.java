package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer1<P1> extends Serializable {
    void accept(P1 p1);

    default Consumer1<P1> andThen(Consumer1<? super P1> after) {
        Objects.requireNonNull(after);

        return (p1) -> {
            accept(p1);
            after.accept(p1);
        };
    }
}
