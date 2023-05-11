package org.tenio.interstellar.service.http.client;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: RequestInterceptor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 09:28
 * &#064;version: 1.0
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
