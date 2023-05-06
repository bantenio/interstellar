package org.tenio.interstellar.dao.mybatis;

import java.util.Map;

public interface SqlTemplate {
    String executeSqlTemplate(String templateFlag, Map<String, Object> params);
}
