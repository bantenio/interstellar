package org.tenio.interstellar.service.http.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: RequestInterceptorChain
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 10:37
 * @version: 1.0
 */
public class SimpleInterceptorChain implements InterceptorChain {
    private final RequestHandler requestHandler;
    private final List<Interceptor> interceptorList;
    private int currentIndex = -1;

    /**
     * 创建空的拦截器请求执行链
     *
     * @param requestHandler 请求执行器
     */
    public SimpleInterceptorChain(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.interceptorList = new ArrayList<>();
    }

    /**
     * 通过给定List创建拦截器请求执行链
     *
     * @param requestHandler  请求执行器
     * @param interceptorList 拦截器链
     */
    public SimpleInterceptorChain(RequestHandler requestHandler,
                                  List<Interceptor> interceptorList) {
        this.requestHandler = requestHandler;
        this.interceptorList = interceptorList;
    }

    // region interceptor list functions
    public int size() {
        return interceptorList.size();
    }

    public boolean isEmpty() {
        return interceptorList.isEmpty();
    }

    public boolean contains(Object o) {
        return interceptorList.contains(o);
    }

    public boolean add(Interceptor interceptor) {
        return interceptorList.add(interceptor);
    }

    public boolean remove(Object o) {
        return interceptorList.remove(o);
    }

    public boolean addAll(Collection<? extends Interceptor> c) {
        return interceptorList.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends Interceptor> c) {
        return interceptorList.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return interceptorList.removeAll(c);
    }

    public void sort(Comparator<? super Interceptor> c) {
        interceptorList.sort(c);
    }

    public void clear() {
        interceptorList.clear();
    }

    public Interceptor get(int index) {
        return interceptorList.get(index);
    }

    public Interceptor set(int index, Interceptor element) {
        return interceptorList.set(index, element);
    }

    public void add(int index, Interceptor element) {
        interceptorList.add(index, element);
    }

    public Interceptor remove(int index) {
        return interceptorList.remove(index);
    }

    public int indexOf(Object o) {
        return interceptorList.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return interceptorList.lastIndexOf(o);
    }

    public boolean removeIf(Predicate<? super Interceptor> filter) {
        return interceptorList.removeIf(filter);
    }
    // endregion

    @Override
    public Response next(RequestBuilder<? extends RequestBuilder<?>> requestBuilder) {
        currentIndex++;
        if (currentIndex == interceptorList.size()) {
            return requestHandler.handle(requestBuilder);
        }
        return interceptorList.get(currentIndex).handle(requestBuilder, this);
    }
}
