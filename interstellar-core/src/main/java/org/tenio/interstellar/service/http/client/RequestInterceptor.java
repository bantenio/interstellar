package org.tenio.interstellar.service.http.client;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: InRequestInterceptor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 11:04
 * @version: 1.0
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
