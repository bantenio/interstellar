package org.tenio.interstellar.service.http.client;

import org.tenio.interstellar.service.http.HttpMethod;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: DeleteRequestBuilder
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 09:51
 * @version: 1.0
 */
public class DeleteRequestBuilder extends RequestBuilder<DeleteRequestBuilder> {
    public DeleteRequestBuilder() {
        super(HttpMethod.DELETE);
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
    public DeleteRequestBuilder setBody(Object body) {
        throw new UnsupportedOperationException("The DELETE method not support request body.");
    }
}
