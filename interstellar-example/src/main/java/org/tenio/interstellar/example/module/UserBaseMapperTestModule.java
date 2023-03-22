package org.tenio.interstellar.example.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.tenio.interstellar.example.panda.dao.UserBaseDao;

public class UserBaseMapperTestModule implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(UserBaseMapperTestModule.class);
    private final UserBaseDao userBaseMapper;

    public UserBaseMapperTestModule(UserBaseDao userBaseMapper) {
        this.userBaseMapper = userBaseMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("UserBase all: {}", userBaseMapper.all());
    }
}
