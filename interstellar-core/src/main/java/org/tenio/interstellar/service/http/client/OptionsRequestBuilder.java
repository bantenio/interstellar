package org.tenio.interstellar.service.http.client;

import org.tenio.interstellar.service.http.HttpMethod;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: OptionsRequestBuilder
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 09:51
 * @version: 1.0
 */
public class OptionsRequestBuilder extends RequestBuilder<OptionsRequestBuilder> {
    public OptionsRequestBuilder() {
        super(HttpMethod.OPTIONS);
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
    public OptionsRequestBuilder setBody(Object body) {
        throw new UnsupportedOperationException("The OPTIONS method not support request body.");
    }
}
