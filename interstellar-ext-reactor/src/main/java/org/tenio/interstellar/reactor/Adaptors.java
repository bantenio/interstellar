package org.tenio.interstellar.reactor;

import org.tenio.interstellar.functions.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

public class Adaptors {
    // region functions
    public static <R> Mono<R> f(Function0<R> function) {
        return f(function, null);
    }

    public static <R> Mono<R> f(Function0<R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply());
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, R> Mono<R> f1(T1 t1, Function1<T1, R> function) {
        return f1(t1, function, null);
    }

    public static <T1, R> Mono<R> f1(T1 t1, Function1<T1, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(t1));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2, R> Mono<R> f2(T1 t1, T2 t2, Function2<T1, T2, R> function) {
        return f2(t1, t2, function, null);
    }

    public static <T1, T2, R> Mono<R> f2(T1 t1, T2 t2, Function2<T1, T2, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(t1, t2));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2, T3, R> Mono<R> f3(T1 t1, T2 t2, T3 t3, Function3<T1, T2, T3, R> function) {
        return f3(t1, t2, t3, function, null);
    }

    public static <T1, T2, T3, R> Mono<R> f3(T1 t1, T2 t2, T3 t3, Function3<T1, T2, T3, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(t1, t2, t3));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2, T3, T4, R> Mono<R> f4(T1 t1, T2 t2, T3 t3, T4 t4, Function4<T1, T2, T3, T4, R> function) {
        return f4(t1, t2, t3, t4, function, null);
    }

    public static <T1, T2, T3, T4, R> Mono<R> f4(T1 t1, T2 t2, T3 t3, T4 t4, Function4<T1, T2, T3, T4, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(t1, t2, t3, t4));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2, T3, T4, T5, R> Mono<R> f5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, Function5<T1, T2, T3, T4, T5, R> function) {
        return f5(t1, t2, t3, t4, t5, function, null);
    }

    public static <T1, T2, T3, T4, T5, R> Mono<R> f5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, Function5<T1, T2, T3, T4, T5, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(t1, t2, t3, t4, t5));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }
    // endregion

    // region consumers
    public static Mono<Void> c(Consumer0 consumer) {
        return c(consumer, null);
    }

    public static Mono<Void> c(Consumer0 consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept());
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1> Mono<Void> c1(T1 t1, Consumer1<T1> consumer) {
        return c1(t1, consumer, null);
    }

    public static <T1> Mono<Void> c1(T1 t1, Consumer1<T1> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(t1));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2> Mono<Void> c2(T1 t1, T2 t2, Consumer2<T1, T2> consumer) {
        return c2(t1, t2, consumer, null);
    }

    public static <T1, T2> Mono<Void> c2(T1 t1, T2 t2, Consumer2<T1, T2> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(t1, t2));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2, T3> Mono<Void> c3(T1 t1, T2 t2, T3 t3, Consumer3<T1, T2, T3> consumer) {
        return c3(t1, t2, t3, consumer, null);
    }

    public static <T1, T2, T3> Mono<Void> c3(T1 t1, T2 t2, T3 t3, Consumer3<T1, T2, T3> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(t1, t2, t3));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2, T3, T4> Mono<Void> c4(T1 t1, T2 t2, T3 t3, T4 t4, Consumer4<T1, T2, T3, T4> consumer) {
        return c4(t1, t2, t3, t4, consumer, null);
    }

    public static <T1, T2, T3, T4> Mono<Void> c4(T1 t1, T2 t2, T3 t3, T4 t4, Consumer4<T1, T2, T3, T4> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(t1, t2, t3, t4));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }

    public static <T1, T2, T3, T4, T5> Mono<Void> c5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, Consumer5<T1, T2, T3, T4, T5> consumer) {
        return c5(t1, t2, t3, t4, t5, consumer, null);
    }

    public static <T1, T2, T3, T4, T5> Mono<Void> c5(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, Consumer5<T1, T2, T3, T4, T5> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(t1, t2, t3, t4, t5));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }
    // endregion
}
