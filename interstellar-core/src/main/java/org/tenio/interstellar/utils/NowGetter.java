package org.tenio.interstellar.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * &#064;author sunkaihan
 */
public interface NowGetter {
    /**
     * 获取时间间隔单位
     *
     * @return TODO
     */
    TimeUnit getTimeUnit();

    /**
     * 记录now的值
     */
    void rememberNowValue();

    /**
     * TODO
     *
     * @return TODO
     */
    Number nowValue();

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    class MilliNowGetter implements NowGetter {
        private final AtomicLong now = new AtomicLong();

        @Override
        public TimeUnit getTimeUnit() {
            return TimeUnit.MILLISECONDS;
        }

        @Override
        public void rememberNowValue() {
            now.set(System.currentTimeMillis());
        }

        @Override
        public Number nowValue() {
            return now.get();
        }
    }

    /**
     * TODO
     * <p>
     * &#064;author:     Ban Tenio
     * &#064;version:    1.0
     */
    class SecondNowGetter implements NowGetter {
        private final AtomicInteger now = new AtomicInteger();

        @Override
        public TimeUnit getTimeUnit() {
            return TimeUnit.SECONDS;
        }

        @Override
        public void rememberNowValue() {
            now.set((int) (System.currentTimeMillis() / 1000));
        }

        @Override
        public Number nowValue() {
            return now.get();
        }
    }
}
