package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Consumer0 extends Serializable {
    void accept();

    default Consumer0 andThen(Consumer0 after) {
        Objects.requireNonNull(after);

        return () -> {
            accept();
            after.accept();
        };
    }
}
