package org.tenio.interstellar.example.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.tenio.interstellar.example.vo.UserVO;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static org.tenio.interstellar.reactor.Adaptors.c1;
import static org.tenio.interstellar.reactor.Adaptors.f2;

public class ReactorTestModule implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(ReactorTestModule.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        asyncCreateAndLogUserVO("sunkaihan", 36)
                .map(UserVO::getName)
                .log()
                .subscribe(name -> log.info("UserVO name: {}", name));
    }

    public UserVO createUserVO(String name, int age) {
        return new UserVO().setName(name).setAge(age);
    }

    public void logUserVO(UserVO userVO) {
        try {
            TimeUnit.of(ChronoUnit.SECONDS).sleep(5);
        } catch (InterruptedException ex) {
            log.warn("logUserVO sleep occurred error.", ex);
        }
        log.info("log UserVO : {}", userVO);
    }

    public Mono<UserVO> asyncCreateAndLogUserVO(String name, int age) {
        return f2(name, age, this::createUserVO, Schedulers.newParallel("createUserVO", 10))
                .subscribeOn(Schedulers.newParallel("createUserVO", 10))
                .doOnNext(userVo -> c1(userVo, this::logUserVO, Schedulers.newParallel("logUserVO", 10))
                        .subscribeOn(Schedulers.newParallel("logUserVO", 10))
                        .log()
                        .subscribe());
    }
}
