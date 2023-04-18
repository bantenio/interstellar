package org.tenio.interstellar.functions;

import java.util.Objects;
import java.io.Serializable;

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

    default void invoke(Object[] args) {
        accept(<#list 1..size as idx>(P${idx}) args[${idx - 1}]<#if idx_has_next>, </#if></#list>);
    }
}