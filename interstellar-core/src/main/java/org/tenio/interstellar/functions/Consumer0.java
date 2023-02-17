package org.tenio.interstellar.functions;

import java.util.Objects;

public interface Consumer0 {
    void accept();

    default Consumer0 andThen(Consumer0 after) {
        Objects.requireNonNull(after);

        return () -> {
            accept();
            after.accept();
        };
    }
}
