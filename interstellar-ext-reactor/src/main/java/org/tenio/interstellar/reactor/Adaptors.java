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


    public static <P1, P2, P3, P4, P5, P6, R> Mono<R> f6(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, Function6<P1, P2, P3, P4, P5, P6, R> function) {
        return f6(p1, p2, p3, p4, p5, p6, function, null);
    }


    public static <P1, P2, P3, P4, P5, P6, R> Mono<R> f6(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, Function6<P1, P2, P3, P4, P5, P6, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(p1, p2, p3, p4, p5, p6));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, R> Mono<R> f7(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, Function7<P1, P2, P3, P4, P5, P6, P7, R> function) {
        return f7(p1, p2, p3, p4, p5, p6, p7, function, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, R> Mono<R> f7(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, Function7<P1, P2, P3, P4, P5, P6, P7, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(p1, p2, p3, p4, p5, p6, p7));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, R> Mono<R> f8(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, Function8<P1, P2, P3, P4, P5, P6, P7, P8, R> function) {
        return f8(p1, p2, p3, p4, p5, p6, p7, p8, function, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, R> Mono<R> f8(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, Function8<P1, P2, P3, P4, P5, P6, P7, P8, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(p1, p2, p3, p4, p5, p6, p7, p8));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> Mono<R> f9(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, Function9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> function) {
        return f9(p1, p2, p3, p4, p5, p6, p7, p8, p9, function, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, R> Mono<R> f9(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, Function9<P1, P2, P3, P4, P5, P6, P7, P8, P9, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> Mono<R> f10(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, Function10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> function) {
        return f10(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, function, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> Mono<R> f10(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, Function10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> Mono<R> f11(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, Function11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> function) {
        return f11(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, function, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> Mono<R> f11(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, Function11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> Mono<R> f12(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, Function12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> function) {
        return f12(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, function, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> Mono<R> f12(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, Function12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
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


    public static <P1, P2, P3, P4, P5, P6> Mono<Void> c6(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, Consumer6<P1, P2, P3, P4, P5, P6> consumer) {
        return c6(p1, p2, p3, p4, p5, p6, consumer, null);
    }


    public static <P1, P2, P3, P4, P5, P6> Mono<Void> c6(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, Consumer6<P1, P2, P3, P4, P5, P6> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(p1, p2, p3, p4, p5, p6));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7> Mono<Void> c7(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, Consumer7<P1, P2, P3, P4, P5, P6, P7> consumer) {
        return c7(p1, p2, p3, p4, p5, p6, p7, consumer, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7> Mono<Void> c7(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, Consumer7<P1, P2, P3, P4, P5, P6, P7> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(p1, p2, p3, p4, p5, p6, p7));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8> Mono<Void> c8(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, Consumer8<P1, P2, P3, P4, P5, P6, P7, P8> consumer) {
        return c8(p1, p2, p3, p4, p5, p6, p7, p8, consumer, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8> Mono<Void> c8(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, Consumer8<P1, P2, P3, P4, P5, P6, P7, P8> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(p1, p2, p3, p4, p5, p6, p7, p8));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9> Mono<Void> c9(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, Consumer9<P1, P2, P3, P4, P5, P6, P7, P8, P9> consumer) {
        return c9(p1, p2, p3, p4, p5, p6, p7, p8, p9, consumer, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9> Mono<Void> c9(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, Consumer9<P1, P2, P3, P4, P5, P6, P7, P8, P9> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(p1, p2, p3, p4, p5, p6, p7, p8, p9));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10> Mono<Void> c10(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, Consumer10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10> consumer) {
        return c10(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, consumer, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10> Mono<Void> c10(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, Consumer10<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11> Mono<Void> c11(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, Consumer11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11> consumer) {
        return c11(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, consumer, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11> Mono<Void> c11(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, Consumer11<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12> Mono<Void> c12(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, Consumer12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12> consumer) {
        return c12(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, consumer, null);
    }


    public static <P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12> Mono<Void> c12(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9, P10 p10, P11 p11, P12 p12, Consumer12<P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, P11, P12> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }
    // endregion
}
