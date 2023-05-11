package org.tenio.interstellar.service.http.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: RequestInterceptorChain
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 10:37
 * &#064;version: 1.0
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

    /**
     * TODO
     *
     * @return TODO
     */
    public int size() {
        return interceptorList.size();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public boolean isEmpty() {
        return interceptorList.isEmpty();
    }

    /**
     * TODO
     *
     * @param o TODO
     * @return TODO
     */
    public boolean contains(Object o) {
        return interceptorList.contains(o);
    }

    /**
     * TODO
     *
     * @param interceptor TODO
     * @return TODO
     */
    public boolean add(Interceptor interceptor) {
        return interceptorList.add(interceptor);
    }

    /**
     * TODO
     *
     * @param o TODO
     * @return TODO
     */
    public boolean remove(Object o) {
        return interceptorList.remove(o);
    }

    /**
     * TODO
     *
     * @param c TODO
     * @return TODO
     */
    public boolean addAll(Collection<? extends Interceptor> c) {
        return interceptorList.addAll(c);
    }

    /**
     * TODO
     *
     * @param index TODO
     * @param c     TODO
     * @return TODO
     */
    public boolean addAll(int index, Collection<? extends Interceptor> c) {
        return interceptorList.addAll(index, c);
    }

    /**
     * TODO
     *
     * @param c TODO
     * @return TODO
     */
    public boolean removeAll(Collection<?> c) {
        return interceptorList.removeAll(c);
    }

    /**
     * TODO
     *
     * @param c TODO
     */
    public void sort(Comparator<? super Interceptor> c) {
        interceptorList.sort(c);
    }

    /**
     * TODO
     */
    public void clear() {
        interceptorList.clear();
    }

    /**
     * TODO
     *
     * @param index TODO
     * @return TODO
     */
    public Interceptor get(int index) {
        return interceptorList.get(index);
    }

    /**
     * TODO
     *
     * @param index   TODO
     * @param element TODO
     * @return TODO
     */
    public Interceptor set(int index, Interceptor element) {
        return interceptorList.set(index, element);
    }

    /**
     * TODO
     *
     * @param index   TODO
     * @param element TODO
     */
    public void add(int index, Interceptor element) {
        interceptorList.add(index, element);
    }

    /**
     * TODO
     *
     * @param index TODO
     * @return TODO
     */
    public Interceptor remove(int index) {
        return interceptorList.remove(index);
    }

    /**
     * TODO
     *
     * @param o TODO
     * @return TODO
     */
    public int indexOf(Object o) {
        return interceptorList.indexOf(o);
    }

    /**
     * TODO
     *
     * @param o TODO
     * @return TODO
     */
    public int lastIndexOf(Object o) {
        return interceptorList.lastIndexOf(o);
    }

    /**
     * TODO
     *
     * @param filter TODO
     * @return TODO
     */
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
