package org.tenio.interstellar.service.http.client;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: OutRequestInterceptor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 11:06
 * &#064;version: 1.0
 */
public abstract class ResponseInterceptor implements Interceptor {
    /**
     * 处理响应对象，并给出返回对象
     *
     * @param response       请求响应
     * @param requestBuilder 请求构建Builder对象
     * @return 处理后返回的响应对象
     */
    protected abstract Response afterRequest(Response response, RequestBuilder<? extends RequestBuilder<?>> requestBuilder);

    @Override
    public Response handle(RequestBuilder<? extends RequestBuilder<?>> requestBuilder, SimpleInterceptorChain chain) {
        Response response = chain.next(requestBuilder);
        return afterRequest(response, requestBuilder);
    }
}
