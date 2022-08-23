package org.tenio.interstellar.event;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

public class MethodStrategy {

    private Object listener;

    private Method method;

    private boolean async;

    private Executor executor;

    public MethodStrategy() {
    }

    public MethodStrategy(Object listener, Method method, boolean async, Executor executor) {
        this.listener = listener;
        this.method = method;
        this.async = async;
        this.executor = executor;
    }

    public Object getListener() {
        return listener;
    }

    public MethodStrategy setListener(Object listener) {
        this.listener = listener;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public MethodStrategy setMethod(Method method) {
        this.method = method;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public MethodStrategy setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public Executor getExecutor() {
        return executor;
    }

    public MethodStrategy setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    @Override
    public final int hashCode() {
        return (31 + method.hashCode()) * 31 + System.identityHashCode(listener);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof MethodStrategy) {
            MethodStrategy that = (MethodStrategy) obj;
            return listener == that.listener && method.equals(that.method) && async == that.async;
        }
        return false;
    }
}
