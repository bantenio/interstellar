package org.tenio.interstellar.functions;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public interface Invoker {
    /**
     * TODO
     *
     * @return TODO
     */
    boolean hasResult();

    /**
     * TODO
     *
     * @param args TODO
     * @return TODO
     */
    Object invoke(Object[] args);
}
