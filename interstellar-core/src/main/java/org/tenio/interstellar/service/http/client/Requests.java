package org.tenio.interstellar.service.http.client;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: Requests
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 09:28
 * &#064;version: 1.0
 */
public class Requests {
    /**
     * GET Method
     */
    public static final String HTTP_METHOD_GET = "GET";
    /**
     * POST Method
     */
    public static final String HTTP_METHOD_POST = "POST";
    /**
     * PUT Method
     */
    public static final String HTTP_METHOD_PUT = "PUT";
    /**
     * DELETE Method
     */
    public static final String HTTP_METHOD_DELETE = "DELETE";
    /**
     * HEAD Method
     */
    public static final String HTTP_METHOD_HEAD = "HEAD";
    /**
     * OPTIONS Method
     */
    public static final String HTTP_METHOD_OPTIONS = "OPTIONS";
    /**
     * TRACE Method
     */
    public static final String HTTP_METHOD_TRACE = "TRACE";

    /**
     * 创建POST请求Builder
     *
     * @return TODO
     */
    public static PostRequestBuilder post() {
        return new PostRequestBuilder();
    }

    /**
     * 创建GET请求Builder
     *
     * @return TODO
     */
    public static GetRequestBuilder get() {
        return new GetRequestBuilder();
    }

    /**
     * 创建PUT请求Builder
     *
     * @return TODO
     */
    public static PutRequestBuilder put() {
        return new PutRequestBuilder();
    }

    /**
     * 创建DELETE请求Builder
     *
     * @return TODO
     */
    public static DeleteRequestBuilder delete() {
        return new DeleteRequestBuilder();
    }

    /**
     * 创建HEAD请求Builder
     *
     * @return TODO
     */
    public static HeadRequestBuilder head() {
        return new HeadRequestBuilder();
    }

    /**
     * 创建OPTIONS请求Builder
     *
     * @return TODO
     */
    public static OptionsRequestBuilder options() {
        return new OptionsRequestBuilder();
    }

    /**
     * 创建TRACE请求Builder
     *
     * @return TODO
     */
    public static TraceRequestBuilder trace() {
        return new TraceRequestBuilder();
    }
}
