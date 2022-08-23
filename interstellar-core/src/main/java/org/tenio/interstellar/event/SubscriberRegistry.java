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

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.WeakCache;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.SetValuedMap;
import org.apache.commons.collections4.iterators.IteratorChain;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.collections4.set.UnmodifiableSet;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

/**
 * Registry of subscribers to a single event bus.
 *
 * @author Colin Decker
 */
public class SubscriberRegistry {

    /**
     * All registered subscribers, indexed by event type.
     *
     * <p>The {@link CopyOnWriteArraySet} values make it easy and relatively lightweight to get an
     * immutable snapshot of all current subscribers to an event without any locking.
     */
    private final ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscriber>> subscribers = MapUtil.newConcurrentHashMap();

    public void register(Object listener, Method method) {
        register(new MethodStrategy(listener, method, false, null));
    }

    public void registerByAsync(Object listener, Method method) {
        register(new MethodStrategy(listener, method, true, null));
    }

    public void register(Object listener, Method method, boolean async, Executor executor) {
        register(new MethodStrategy(listener, method, async, executor));
    }

    public void register(MethodStrategy methodStrategy) {
        Class<?>[] parameterTypes = methodStrategy.getMethod().getParameterTypes();
        Class<?> eventType = parameterTypes[0];
        CopyOnWriteArraySet<Subscriber> eventSubscribers = subscribers.get(eventType);
        if (eventSubscribers == null) {
            CopyOnWriteArraySet<Subscriber> newSet = new CopyOnWriteArraySet<>();
            eventSubscribers = ObjectUtil.defaultIfNull(subscribers.putIfAbsent(eventType, newSet), newSet);
        }

        eventSubscribers.add(Subscriber.create(methodStrategy));
    }


    /**
     * Registers all subscriber methods on the given listener object.
     */
    public void register(Collection<MethodStrategy> methodList) {
        SetValuedMap<Class<?>, Subscriber> listenerMethods = findAllSubscribers(methodList);
        for (Entry<Class<?>, Collection<Subscriber>> entry : listenerMethods.asMap().entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<Subscriber> eventMethodsInListener = entry.getValue();
            CopyOnWriteArraySet<Subscriber> eventSubscribers = subscribers.get(eventType);
            if (eventSubscribers == null) {
                CopyOnWriteArraySet<Subscriber> newSet = new CopyOnWriteArraySet<>();
                eventSubscribers =
                        ObjectUtil.defaultIfNull(subscribers.putIfAbsent(eventType, newSet), newSet);
            }
            eventSubscribers.addAll(eventMethodsInListener);
        }
    }

    public void unregister(Object listener, Method method) {
        unregister(new MethodStrategy(listener, method, false, null));
    }

    public void unregisterByAsync(Object listener, Method method) {
        unregister(new MethodStrategy(listener, method, true, null));
    }

    public void unregister(Object listener, Method method, boolean async, Executor executor) {
        unregister(new MethodStrategy(listener, method, async, executor));
    }


    public int unregister(MethodStrategy methodStrategy) {
        Class<?>[] parameterTypes = methodStrategy.getMethod().getParameterTypes();
        Class<?> eventType = parameterTypes[0];
        CopyOnWriteArraySet<Subscriber> eventSubscribers = subscribers.get(eventType);
        int unregisterSize = 0;
        if (eventSubscribers != null) {
            eventSubscribers.remove(Subscriber.create(methodStrategy));
            unregisterSize++;
        }
        return unregisterSize;
    }

    /**
     * Unregisters all subscribers on the given listener object.
     */
    public int unregister(List<MethodStrategy> methodList) {
        SetValuedMap<Class<?>, Subscriber> listenerMethods = findAllSubscribers(methodList);

        for (Entry<Class<?>, Collection<Subscriber>> entry : listenerMethods.asMap().entrySet()) {
            Class<?> eventType = entry.getKey();
            Collection<Subscriber> listenerMethodsForType = entry.getValue();

            CopyOnWriteArraySet<Subscriber> currentSubscribers = subscribers.get(eventType);
            if (currentSubscribers == null) {
                return 0;
            }
            int beforeRemovedSize = currentSubscribers.size();
            if (!currentSubscribers.removeAll(listenerMethodsForType)) {
                return 0;
            }
            return beforeRemovedSize - currentSubscribers.size();
        }
        return 0;
    }

    /**
     * Gets an iterator representing an immutable snapshot of all subscribers to the given event at
     * the time this method is called.
     */
    protected Iterator<Subscriber> getSubscribers(Object event) {
        Set<Class<?>> eventTypes = flattenHierarchy(event.getClass());

        List<Iterator<Subscriber>> subscriberIterators = new ArrayList<>(eventTypes.size());

        for (Class<?> eventType : eventTypes) {
            CopyOnWriteArraySet<Subscriber> eventSubscribers = subscribers.get(eventType);
            if (eventSubscribers != null) {
                // eager no-copy snapshot
                subscriberIterators.add(eventSubscribers.iterator());
            }
        }

        return new IteratorChain(subscriberIterators.iterator());
    }

    /**
     * Returns all subscribers for the given listener grouped by the type of event they subscribe to.
     */
    protected SetValuedMap<Class<?>, Subscriber> findAllSubscribers(Collection<MethodStrategy> methodList) {

        SetValuedMap<Class<?>, Subscriber> methodsInListener = new HashSetValuedHashMap<>();
        for (MethodStrategy methodStrategy : methodList) {
            Method method = methodStrategy.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Class<?> eventType = parameterTypes[0];
            methodsInListener.put(eventType, Subscriber.create(methodStrategy));
        }
        return methodsInListener;
    }

    /**
     * Global cache of classes to their flattened hierarchy of supertypes.
     */
    private static final WeakCache<Class<?>, Set<Class<?>>> flattenHierarchyCache =
            CacheUtil.newWeakCache(0);

    /**
     * Flattens a class's type hierarchy into a set of {@code Class} objects including all
     * superclasses (transitively) and all interfaces implemented by these superclasses.
     */
    static Set<Class<?>> flattenHierarchy(Class<?> concreteClass) {
        return flattenHierarchyCache.get(concreteClass, () -> SetUtils.unmodifiableSet(concreteClass));
    }
}
