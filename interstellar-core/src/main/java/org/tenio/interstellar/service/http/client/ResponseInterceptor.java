package org.tenio.interstellar.service.http.client;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: OutRequestInterceptor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 11:06
 * @version: 1.0
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
