package org.tenio.interstellar.dao.mybatis;

import cn.hutool.core.text.CharSequenceUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class TemplateSqlProvider implements ProviderMethodResolver {
    private static final Logger log = LoggerFactory.getLogger(TemplateSqlProvider.class);
    private static SqlTemplate sqlTemplate;

    /**
     * TODO
     *
     * @param sqlTemplate TODO
     */
    public static void setSqlTemplate(SqlTemplate sqlTemplate) {
        TemplateSqlProvider.sqlTemplate = sqlTemplate;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    protected SqlTemplate getSqlTemplate() {
        return TemplateSqlProvider.sqlTemplate;
    }

    /**
     * TODO
     *
     * @param queryFlag       TODO
     * @param params          TODO
     * @param providerContext TODO
     * @return TODO
     */
    public String query(@Param("queryFlag") String queryFlag, @Param("params") Map<String, Object> params, ProviderContext providerContext) {
        if (CharSequenceUtil.isBlank(queryFlag)) {
            throw new IllegalArgumentException("The queryFlag is empty.");
        }
        if (log.isDebugEnabled()) {
            log.debug("Will invoke queryFlag is '{}'", queryFlag);
        }
        return getSqlTemplate().executeSqlTemplate(queryFlag, params);
    }
}
