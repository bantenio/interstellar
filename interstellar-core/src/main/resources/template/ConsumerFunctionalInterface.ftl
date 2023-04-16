package org.tenio.interstellar.functions;

import java.util.Objects;

@FunctionalInterface
public interface Consumer${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>> extends Serializable {
    void accept(<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>);

    default Consumer${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>> andThen(Consumer${size}<<#list 1..size as idx>? super P${idx}<#if idx_has_next>, </#if></#list>> after) {
        Objects.requireNonNull(after);

        return (<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>) -> {
            accept(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>);
            after.accept(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>);
        };
    }


    public static <<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>> Mono<Void> c${size}(<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>, Consumer${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>> consumer) {
        return c${size}(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>, consumer, null);
    }


    public static <<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>> Mono<Void> c${size}(<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>, Consumer${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>> consumer, Scheduler scheduler) {
        Mono<Void> task = Mono.fromRunnable(() -> consumer.accept(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>));
        if (scheduler != null) {
            task.publishOn(scheduler);
        }
        return task;
    }
}