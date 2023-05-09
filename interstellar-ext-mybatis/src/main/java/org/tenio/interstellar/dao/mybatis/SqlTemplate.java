package org.tenio.interstellar.dao.mybatis;

import java.util.Map;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public interface SqlTemplate {
    /**
     * TODO
     *
     * @param templateFlag TODO
     * @param params       TODO
     * @return TODO
     */
    String executeSqlTemplate(String templateFlag, Map<String, Object> params);
}
