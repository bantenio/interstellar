package org.tenio.interstellar.service.http.client;

import org.tenio.interstellar.service.http.HttpMethod;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: PutRequestBuilder
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 09:51
 * @version: 1.0
 */
public class PutRequestBuilder extends RequestBuilder<PutRequestBuilder> {
    public PutRequestBuilder() {
        super(HttpMethod.PUT);
    }

    @Override
    public final boolean hasBody() {
        return true;
    }
}
