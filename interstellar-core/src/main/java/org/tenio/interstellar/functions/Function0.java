package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function0<R> extends Serializable, Invoker {
    R apply();

    default <V> Function0<V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return () -> after.apply(apply());
    }

    default Object invoke(Object[] args) {
        return apply();
    }

    default boolean hasResult() {
        return true;
    }
}