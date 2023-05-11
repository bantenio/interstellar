package org.tenio.interstellar.service.http.client;

import org.tenio.interstellar.service.http.HttpMethod;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: HeadRequestBuilder
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 09:51
 * &#064;version: 1.0
 */
public class HeadRequestBuilder extends RequestBuilder<HeadRequestBuilder> {
    /**
     * Http Head 方法请求构造器
     */
    public HeadRequestBuilder() {
        super(HttpMethod.HEAD);
    }

    @Override
    public final boolean hasBody() {
        return false;
    }

    @Override
    public final Object getBody() {
        return null;
    }

    @Override
    public HeadRequestBuilder setBody(Object body) {
        throw new UnsupportedOperationException("The HEAD method not support request body.");
    }
}
