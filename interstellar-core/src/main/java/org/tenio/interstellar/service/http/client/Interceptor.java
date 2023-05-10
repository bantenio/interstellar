package org.tenio.interstellar.service.http.client;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: RequestInterceptor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 09:28
 * @version: 1.0
 */
public interface Interceptor {
    /**
     * 拦截器处理请求和返回的响应
     *
     * @param requestBuilder 请求构建Builder
     * @param chain          请求拦截器执行链
     * @return 返回的Http请求响应对象
     */
    Response handle(RequestBuilder<? extends RequestBuilder<?>> requestBuilder, SimpleInterceptorChain chain);
}
