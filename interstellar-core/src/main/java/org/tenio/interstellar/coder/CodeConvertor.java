package org.tenio.interstellar.coder;

/**
 * TODO
 * <p>
 * &#064;author:      Ban Tenio
 * &#064;version:    1.0
 */
public interface CodeConvertor {

    /**
     * TODO
     *
     * @param number TODO
     * @return TODO
     */
    String encode(long number);

    /**
     * TODO
     *
     * @param code TODO
     * @return TODO
     */
    long decode(String code);
}
