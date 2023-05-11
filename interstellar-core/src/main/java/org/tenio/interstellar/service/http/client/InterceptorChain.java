package org.tenio.interstellar.service.http.client;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: RequestInterceptorChain
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 11:01
 * &#064;version: 1.0
 */
public interface InterceptorChain {

    /**
     * 执行下一个拦截器
     *
     * @param requestBuilder 请求构建Builder对象
     * @return Http请求响应对象
     */
    Response next(RequestBuilder<? extends RequestBuilder<?>> requestBuilder);
}
