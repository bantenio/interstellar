package org.tenio.interstellar.unique.snow;

import org.tenio.interstellar.coder.CodeConvertor;
import org.tenio.interstellar.unique.IUniqueGenerator;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class CoderConvertorSnowUniqueGenerator implements IUniqueGenerator {
    private final ISnowUnique snowUnique;
    private final CodeConvertor codeConvertor;

    /**
     *
     * TODO
     *
     * @param snowUnique TODO
     * @param codeConvertor TODO
     */
    public CoderConvertorSnowUniqueGenerator(ISnowUnique snowUnique,
                                             CodeConvertor codeConvertor) {
        this.snowUnique = snowUnique;
        this.codeConvertor = codeConvertor;
    }

    @Override
    public String nextUnique() {
        return codeConvertor.encode(snowUnique.next());
    }
}
