package org.tenio.interstellar.service.http.client;

import org.tenio.interstellar.service.http.HttpMethod;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: PutRequestBuilder
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 09:51
 * &#064;version: 1.0
 */
public class PutRequestBuilder extends RequestBuilder<PutRequestBuilder> {
    /**
     * Http Put 请求构造器
     */
    public PutRequestBuilder() {
        super(HttpMethod.PUT);
    }

    @Override
    public final boolean hasBody() {
        return true;
    }
}
