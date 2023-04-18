package org.tenio.interstellar.functions;

import java.util.Objects;
import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface Function${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, R> extends Serializable {
    R apply(<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>);

    default <V> Function${size}<<#list 1..size as idx>P${idx}<#if idx_has_next>, </#if></#list>, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);

        return (<#list 1..size as idx>P${idx} p${idx}<#if idx_has_next>, </#if></#list>) -> after.apply(apply(<#list 1..size as idx>p${idx}<#if idx_has_next>, </#if></#list>));
    }

    default Object invoke(Object[] args) {
        return apply(<#list 1..size as idx>(P${idx}) args[${idx - 1}]<#if idx_has_next>, </#if></#list>);
    }
}