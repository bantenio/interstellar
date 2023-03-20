package org.tenio.interstellar.lang;

import java.util.function.Function;

/**
 * @author sunkaihan
 * @date 2022-04-18
 */
public interface Copyable {
    Copyable copy();
    Copyable copy(Function<Object, ?> cloner);
}
