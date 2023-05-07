package org.tenio.interstellar.lang;

import java.util.function.Function;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public interface Copyable {
    /**
     * TODO
     *
     * @return TODO
     */
    Copyable copy();

    /**
     * TODO
     *
     * @param cloner TODO
     * @return TODO
     */
    Copyable copy(Function<Object, ?> cloner);
}
