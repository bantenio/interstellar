package org.tenio.interstellar.functions;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface Function${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, R> extends Serializable {
    R apply(<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>);

    default <V> Function${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>) -> after.apply(apply(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>));
    }


    public static <<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, R> Mono<R> f${size}(<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>, Function${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, R> function) {
        return f${size}(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>, function, null);
    }


    public static <<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, R> Mono<R> f${size}(<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>, Function${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, R> function, Scheduler scheduler) {
        Mono<R> task = Mono.fromCallable(() -> function.apply(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }
}