package org.tenio.interstellar.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * &#064;author sunkaihan
 */
public class SystemClock {

    private final static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
        Thread thread = new Thread(runnable, "System Clock");
        thread.setDaemon(true);
        return thread;
    });

    private final long period;
    private final NowGetter getter;

    private SystemClock(long period, NowGetter getter) {
        this.period = period;
        this.getter = getter;
        getter.rememberNowValue();
        scheduleClockUpdating();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public static long nowMilli() {
        return InstanceHolder.MILLI_INSTANCE.getter.nowValue().longValue();
    }

    private long currentTimeMillis() {
        return nowMilli();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public static int nowSecond() {
        return InstanceHolder.SECOND_INSTANCE.getter.nowValue().intValue();
    }

    private void scheduleClockUpdating() {
        scheduler.scheduleAtFixedRate(getter::rememberNowValue, period, period, getter.getTimeUnit());
    }

    private static class InstanceHolder {
        public static final SystemClock MILLI_INSTANCE = new SystemClock(1, new NowGetter.MilliNowGetter());
        public static final SystemClock SECOND_INSTANCE = new SystemClock(1, new NowGetter.SecondNowGetter());
    }
}
