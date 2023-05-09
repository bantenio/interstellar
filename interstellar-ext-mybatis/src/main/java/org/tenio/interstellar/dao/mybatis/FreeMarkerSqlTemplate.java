package org.tenio.interstellar.dao.mybatis;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class FreeMarkerSqlTemplate implements SqlTemplate {
    private final Configuration cfg;

    /**
     *
     * TODO
     *
     * @param cfg TODO
     */
    public FreeMarkerSqlTemplate(Configuration cfg) {
        this.cfg = cfg;
    }

    @Override
    public String executeSqlTemplate(String templateFlag, Map<String, Object> params) {
        try {
            Template template = cfg.getTemplate(templateFlag);
            StringWriter out = new StringWriter();
            template.process(params, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("generated the flag '" + templateFlag + "' sql occurred error.", e);
        }
    }
}
