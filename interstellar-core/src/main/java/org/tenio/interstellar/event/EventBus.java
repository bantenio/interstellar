/*
 * Copyright (C) 2007 The Guava Authors
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

import cn.hutool.core.util.ObjectUtil;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Liujie
 * @since 10.0
 */
public class EventBus {
    private static final Logger logger = Logger.getLogger(EventBus.class.getName());
    private final String identifier;
    private final SubscriberExceptionHandler exceptionHandler;

    private final SubscriberRegistry subscribers = new SubscriberRegistry();
    private final Dispatcher dispatcher;

    /**
     * Creates a new EventBus named "default".
     */
    public EventBus() {
        this("default");
    }

    /**
     * Creates a new EventBus with the given {@code identifier}.
     *
     * @param identifier a brief name for this bus, for logging purposes. Should be a valid Java
     *                   identifier.
     */
    public EventBus(String identifier) {
        this(identifier, Dispatcher.perThreadDispatchQueue(), LoggingHandler.INSTANCE);
    }

    /**
     * Creates a new EventBus with the given {@link SubscriberExceptionHandler}.
     *
     * @param exceptionHandler Handler for subscriber exceptions.
     * @since 16.0
     */
    public EventBus(SubscriberExceptionHandler exceptionHandler) {
        this("default", Dispatcher.perThreadDispatchQueue(), exceptionHandler);
    }

    protected EventBus(String identifier, Dispatcher dispatcher, SubscriberExceptionHandler exceptionHandler) {
        if (ObjectUtil.isAllEmpty(identifier, dispatcher, exceptionHandler)) {
            throw new NullPointerException();
        }
        this.identifier = identifier;
        this.dispatcher = dispatcher;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Returns the identifier for this event bus.
     *
     * @since 19.0
     */
    public final String identifier() {
        return identifier;
    }


    /**
     * Handles the given exception thrown by a subscriber with the given context.
     */
    void handleSubscriberException(Throwable e, SubscriberExceptionContext context) {
        if (ObjectUtil.isAllEmpty(e, context)) {
            throw new NullPointerException();
        }
        try {
            exceptionHandler.handleException(e, context);
        } catch (Throwable e2) {
            // if the handler threw an exception... well, just log it
            logger.log(Level.SEVERE, String.format(Locale.ROOT, "Exception %s thrown while handling exception: %s", e2, e), e2);
        }
    }

    /**
     * Registers all subscriber methods on {@code object} to receive events.
     */
    public void register(MethodStrategy methodStrategy) {
        subscribers.register(methodStrategy);
    }

    /**
     * Registers all subscriber methods on {@code object} to receive events.
     */
    public void register(List<MethodStrategy> methodList) {
        subscribers.register(methodList);
    }

    public void unregister(MethodStrategy methodStrategy) {
        subscribers.unregister(methodStrategy);
    }

    /**
     * Unregisters all subscriber methods on a registered {@code object}.
     *
     * @throws IllegalArgumentException if the object was not previously registered.
     */
    public void unregister(List<MethodStrategy> methodList) {
        subscribers.unregister(methodList);
    }

    /**
     * Posts an event to all registered subscribers. This method will return successfully after the
     * event has been posted to all subscribers, and regardless of any exceptions thrown by
     * subscribers.
     *
     * <p>If no subscribers have been subscribed for {@code event}'s class, and {@code event} is not
     * already a {@link DeadEvent}, it will be wrapped in a DeadEvent and reposted.
     *
     * @param event event to post.
     */
    public void post(Object event, SubscriberExceptionHandler exceptionHandler) {
        Iterator<Subscriber> eventSubscribers = subscribers.getSubscribers(event);
        SubscriberExceptionContext subscriberExceptionContext = new SubscriberExceptionContext()
                .setEventBus(this)
                .setEvent(event);
        if (eventSubscribers.hasNext()) {
            dispatcher.dispatch(event, eventSubscribers, subscriberExceptionContext, exceptionHandler);
        } else if (!(event instanceof DeadEvent)) {
            // the event had no subscribers and was not itself a DeadEvent
            post(new DeadEvent(this, event), exceptionHandler);
        }
    }

    public void post(Object event) {
        post(event, exceptionHandler);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + identifier;
    }

    /**
     * Simple logging handler for subscriber exceptions.
     */
    static final class LoggingHandler implements SubscriberExceptionHandler {
        static final LoggingHandler INSTANCE = new LoggingHandler();

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            Logger logger = logger(context);
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, message(context), exception);
            }
        }

        private static Logger logger(SubscriberExceptionContext context) {
            return Logger.getLogger(EventBus.class.getName() + "." + context.getEventBus().identifier());
        }

        private static String message(SubscriberExceptionContext context) {
            Method method = context.getSubscriberMethod();
            return "Exception thrown by subscriber method " + method.getName() + '(' + method.getParameterTypes()[0].getName() + ')' + " on subscriber " + context.getSubscriber() + " when dispatching event: " + context.getEvent();
        }
    }
}
