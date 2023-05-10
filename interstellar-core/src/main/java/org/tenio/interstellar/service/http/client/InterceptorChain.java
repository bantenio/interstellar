package org.tenio.interstellar.service.http.client;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: RequestInterceptorChain
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 11:01
 * @version: 1.0
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
