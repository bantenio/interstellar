package org.tenio.interstellar.service.http.client;

import org.tenio.interstellar.service.http.HttpMethod;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: OptionsRequestBuilder
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 09:51
 * &#064;version: 1.0
 */
public class OptionsRequestBuilder extends RequestBuilder<OptionsRequestBuilder> {
    /**
     * Http Options 请求构造器
     */
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
