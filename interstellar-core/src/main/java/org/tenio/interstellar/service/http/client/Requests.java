package org.tenio.interstellar.service.http.client;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: Requests
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 09:28
 * @version: 1.0
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

    public static PostRequestBuilder post() {
        return new PostRequestBuilder();
    }

    public static GetRequestBuilder get() {
        return new GetRequestBuilder();
    }

    public static PutRequestBuilder put() {
        return new PutRequestBuilder();
    }

    public static DeleteRequestBuilder delete() {
        return new DeleteRequestBuilder();
    }

    public static HeadRequestBuilder head() {
        return new HeadRequestBuilder();
    }

    public static OptionsRequestBuilder options() {
        return new OptionsRequestBuilder();
    }

    public static TraceRequestBuilder trace() {
        return new TraceRequestBuilder();
    }
}
