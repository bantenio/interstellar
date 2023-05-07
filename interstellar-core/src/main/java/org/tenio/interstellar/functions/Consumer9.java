package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.util.Objects;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 *
 * @param <P1> TODO
 * @param <P2> TODO
 * @param <P3> TODO
 * @param <P4> TODO
 * @param <P5> TODO
 * @param <P6> TODO
 * @param <P7> TODO
 * @param <P8> TODO
 * @param <P9> TODO
 */
@FunctionalInterface
public interface Consumer9<P1, P2, P3, P4, P5, P6, P7, P8, P9> extends Serializable, Invoker {
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
     */
    void accept(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9);

    /**
     *
     * TODO
     *
     * @param after TODO
     * @return TODO
     */
    default Consumer9<P1, P2, P3, P4, P5, P6, P7, P8, P9> andThen(Consumer9<? super P1, ? super P2, ? super P3, ? super P4, ? super P5, ? super P6, ? super P7, ? super P8, ? super P9> after) {
        Objects.requireNonNull(after);

        return (p1, p2, p3, p4, p5, p6, p7, p8, p9) -> {
            accept(p1, p2, p3, p4, p5, p6, p7, p8, p9);
            after.accept(p1, p2, p3, p4, p5, p6, p7, p8, p9);
        };
    }

    default Object invoke(Object[] args) {
        accept((P1) args[0], (P2) args[1], (P3) args[2], (P4) args[3], (P5) args[4], (P6) args[5], (P7) args[6], (P8) args[7], (P9) args[8]);
        return null;
    }

    default boolean hasResult() {
        return false;
    }
}