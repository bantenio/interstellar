package org.tenio.interstellar.service.http.client;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: RequestExecutor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 10:47
 * @version: 1.0
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
