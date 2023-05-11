package org.tenio.interstellar.service.http.client;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: InRequestInterceptor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 11:04
 * &#064;version: 1.0
 */
public abstract class RequestInterceptor implements Interceptor {
    /**
     * 请求前处理请求
     *
     * @param requestBuilder 请求构建Builder对象
     */
    protected abstract void preRequest(RequestBuilder<? extends RequestBuilder<?>> requestBuilder);

    @Override
    public Response handle(RequestBuilder<? extends RequestBuilder<?>> requestBuilder, SimpleInterceptorChain chain) {
        preRequest(requestBuilder);
        return chain.next(requestBuilder);
    }
}
