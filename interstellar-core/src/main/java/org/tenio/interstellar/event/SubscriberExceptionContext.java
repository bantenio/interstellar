/*
 * Copyright (C) 2013 The Guava Authors
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

import java.lang.reflect.Method;

/**
 * Context for an exception thrown by a subscriber.
 *
 * @since 16.0
 */
public class SubscriberExceptionContext {
    private EventBus eventBus;
    private Object event;
    private Object subscriber;
    private Method subscriberMethod;

    /**
     * @param eventBus         The {@link EventBus} that handled the event and the subscriber. Useful for
     *                         broadcasting a new event based on the error.
     * @param event            The event object that caused the subscriber to throw.
     * @param subscriber       The source subscriber context.
     * @param subscriberMethod the subscribed method.
     */
    SubscriberExceptionContext(EventBus eventBus, Object event, Object subscriber, Method subscriberMethod) {
        if (eventBus == null || event == null || subscriber == null || subscriberMethod == null){
            throw new NullPointerException();
        }
        this.eventBus = eventBus;
        this.event = event;
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }

    SubscriberExceptionContext() {

    }

    SubscriberExceptionContext setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
        return this;
    }

    SubscriberExceptionContext setEvent(Object event) {
        this.event = event;
        return this;
    }

    SubscriberExceptionContext setSubscriber(Object subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    SubscriberExceptionContext setSubscriberMethod(Method subscriberMethod) {
        this.subscriberMethod = subscriberMethod;
        return this;
    }

    /**
     * @return The {@link EventBus} that handled the event and the subscriber. Useful for broadcasting
     * a new event based on the error.
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * @return The event object that caused the subscriber to throw.
     */
    public Object getEvent() {
        return event;
    }

    /**
     * @return The object context that the subscriber was called on.
     */
    public Object getSubscriber() {
        return subscriber;
    }

    /**
     * @return The subscribed method that threw the exception.
     */
    public Method getSubscriberMethod() {
        return subscriberMethod;
    }


}
