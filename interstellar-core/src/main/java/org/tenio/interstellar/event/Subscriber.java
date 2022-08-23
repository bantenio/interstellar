/*
 * Copyright (C) 2014 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.tenio.interstellar.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * A subscriber method on a specific object, plus the executor that should be used for dispatching
 * events to it.
 *
 * <p>Two subscribers are equivalent when they refer to the same method on the same object (not
 * class). This property is used to ensure that no subscriber method is registered more than once.
 *
 * @author Colin Decker
 */
public class Subscriber {

    /**
     * Creates a {@code Subscriber} for {@code method} on {@code listener}.
     */
    public static Subscriber create(MethodStrategy methodStrategy) {
        return create(methodStrategy.isAsync(),
                methodStrategy.getListener(),
                methodStrategy.getMethod(),
                methodStrategy.getExecutor());
    }

    /**
     * Creates a {@code Subscriber} for {@code method} on {@code listener}.
     */
    public static Subscriber create(boolean async, Object listener, Method method, Executor executor) {
        return new SynchronizedSubscriber(async, listener, method, executor);
    }

    /**
     * The object with the subscriber method.
     */
    private final Object listener;

    /**
     * Subscriber method.
     */
    private final Method method;

    private final boolean async;

    /**
     * Executor to use for dispatching events to this subscriber.
     */
    private final Executor executor;

    protected Subscriber(boolean async, Object listener, Method method, Executor executor) {
        this.async = async;
        this.listener = Objects.requireNonNull(listener);
        this.method = method;
        method.setAccessible(true);

        this.executor = async ? executor : Runnable::run;
    }

    /**
     * Dispatches {@code event} to this subscriber using the proper executor.
     */
    protected final void dispatchEvent(final Object event,
                                       final SubscriberExceptionContext subscriberExceptionContext,
                                       final SubscriberExceptionHandler exceptionHandler) {
        executor.execute(
                () -> {
                    try {
                        invokeSubscriberMethod(event);
                    } catch (InvocationTargetException e) {
                        if (exceptionHandler != null) {
                            subscriberExceptionContext.setSubscriber(this).setSubscriberMethod(this.method);
                            exceptionHandler.handleException(e, subscriberExceptionContext);
                        }
                    }
                });
    }


    /**
     * Invokes the subscriber method. This method can be overridden to make the invocation
     * synchronized.
     */
    protected void invokeSubscriberMethod(Object event) throws InvocationTargetException {
        try {
            method.invoke(listener, Objects.requireNonNull(event));
        } catch (IllegalArgumentException e) {
            throw new Error("Method rejected target/argument: " + event, e);
        } catch (IllegalAccessException e) {
            throw new Error("Method became inaccessible: " + event, e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw e;
        }
    }

    public boolean isAsync() {
        return this.async;
    }


    @Override
    public final int hashCode() {
        return (31 + method.hashCode()) * 31 + System.identityHashCode(listener);
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof Subscriber) {
            Subscriber that = (Subscriber) obj;
            return listener == that.listener && method.equals(that.method);
        }
        return false;
    }

    /**
     * Subscriber that synchronizes invocations of a method to ensure that only one thread may enter
     * the method at a time.
     */
    static final class SynchronizedSubscriber extends Subscriber {

        private SynchronizedSubscriber(boolean isAsync, Object target, Method method, Executor executor) {
            super(isAsync, target, method, executor);
        }

        @Override
        protected void invokeSubscriberMethod(Object event) throws InvocationTargetException {
            synchronized (this) {
                super.invokeSubscriberMethod(event);
            }
        }
    }
}
