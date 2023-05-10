package org.tenio.interstellar.service.http.client;

import org.tenio.interstellar.service.http.HttpMethod;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: HeadRequestBuilder
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 09:51
 * @version: 1.0
 */
public class HeadRequestBuilder extends RequestBuilder<HeadRequestBuilder> {
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
