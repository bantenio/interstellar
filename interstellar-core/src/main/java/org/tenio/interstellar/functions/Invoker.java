package org.tenio.interstellar.functions;

public interface Invoker {

    boolean hasResult();

    Object invoke(Object[] args);
}
