package org.tenio.interstellar.functions;

import java.util.Objects;
import java.util.function.Function;

public interface Function0<R> {
    R apply();

    default <V> Function0<V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return () -> after.apply(apply());
    }
}
