package org.tenio.interstellar.service.http.client;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: RequestExecutor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 10:47
 * &#064;version: 1.0
 */
public interface RequestHandler {
    /**
     * 执行最终Http请求，并返回Response
     *
     * @param requestBuilder 请求Builder对象
     * @return HTTP请求返回响应对象
     */
    Response handle(RequestBuilder<? extends RequestBuilder<?>> requestBuilder);
}
