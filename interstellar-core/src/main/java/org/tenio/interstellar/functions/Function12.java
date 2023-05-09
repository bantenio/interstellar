package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 *
 * @param <P1>  TODO
 * @param <P2>  TODO
 * @param <P3>  TODO
 * @param <P4>  TODO
 * @param <P5>  TODO
 * @param <P6>  TODO
 * @param <P7>  TODO
 * @param <P8>  TODO
 * @param <P9>  TODO
 * @param <P10> TODO
 * @param <P11> TODO
 * @param <P12> TODO
 * @param <R>   TODO
 */
@FunctionalInterface
public interface Function12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> extends Serializable, Invoker {
    /**
     *
     * TODO
     *
     * @param p1 TODO
     * @param p2 TODO
     * @param p3 TODO
     * @param p4 TODO
     * @param p5 TODO
     * @param p6 TODO
     * @param p7 TODO
     * @param p8 TODO
     * @param p9 TODO
     * @param p10 TODO
     * @param p11 TODO
     * @param p12 TODO
     * @return TODO
     */
    R apply(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12);

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     * @param <V> TODO
     */
    default <V> Function12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12) -> after.apply(apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
    }

    default Object invoke(Object[] args) {
        return apply((P1) args[0], (P2) args[1], (P3) args[2], (P4) args[3], (P5) args[4], (P6) args[5], (P7) args[6], (P8) args[7], (P9) args[8], (P10) args[9], (P11) args[10], (P12) args[11]);
    }


    default boolean hasResult() {
        return true;
    }
}