package org.tenio.interstellar;

import cn.hutool.core.text.CharSequenceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        setEnv();
        configReactor();
        SpringApplication.run(App.class, args);
    }

    protected static void setEnv() {
        String env = System.getProperty("env");
        String area = System.getProperty("area");
        if (log.isDebugEnabled()) {
            log.debug("application context env was: {}, area: {}", env, area);
        }
        if (CharSequenceUtil.equalsAny(env, "local", "stage")) {
            System.setProperty("env", "local");
        } else if (CharSequenceUtil.startWithIgnoreCase(env, "ewd")) {
            System.setProperty("env", "test");
        } else {
            System.setProperty("env", "online");
        }
    }

    private static void configReactor() {
        System.setProperty("reactor.netty.ioWorkerCount", "40");
        System.setProperty("react.netty.ioSelectCount", "4");
    }
}
