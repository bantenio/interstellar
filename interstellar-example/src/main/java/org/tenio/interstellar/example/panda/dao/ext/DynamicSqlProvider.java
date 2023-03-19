package org.tenio.interstellar.example.panda.dao.ext;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tenio.interstellar.example.panda.model.UserBase;
import org.tenio.interstellar.example.panda.model.UserBaseExample;

public class DynamicSqlProvider implements ProviderMethodResolver {

    private static final Logger log = LoggerFactory.getLogger(DynamicSqlProvider.class);

    public CharSequence all(UserBase record, UserBaseExample example, ProviderContext providerContext) {
        log.debug("ProviderContext: databaseId: {}, mapperMethod: {}, mapperType: {}",
                providerContext.getDatabaseId(),
                providerContext.getMapperMethod(),
                providerContext.getMapperType().getSimpleName());
        return "SELECT * FROM user_base";
    }
}
