package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.service.http.MediaType;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: JsonHandler
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 16:02
 * &#064;version: 1.0
 */
public interface JsonRequestHandler extends StringHandler {

    ObjectMapper getObjectMapper();

    boolean withContentType();

    default void preHandle(RequestBuilder<? extends RequestBuilder<?>> requestBuilder) {
        if (requestBuilder.hasBody()) {
            Object data = requestBuilder.getBody();
            if (data != null) {
                data = getBody(data);
                try {
                    data = getObjectMapper().writeValueAsString(data);
                    requestBuilder.setBody(data);
                } catch (JsonProcessingException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        if (withContentType()) {
            requestBuilder.getHttpHeader().setContentType(MediaType.APPLICATION_JSON);
        }
    }
}
