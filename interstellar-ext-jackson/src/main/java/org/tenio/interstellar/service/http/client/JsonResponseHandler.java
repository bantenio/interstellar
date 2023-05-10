package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: JsonResponseHandler
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 16:09
 * @version: 1.0
 */
public interface JsonResponseHandler extends StringHandler {
    ObjectMapper getObjectMapper();

    Class<?> getResponseType();

    TypeReference<?> getTypeReference();

    default Response afterHandle(Response response, RequestBuilder<? extends RequestBuilder<?>> requestBuilder) {
        Class<?> responseType = getResponseType();
        TypeReference<?> typeReference = getTypeReference();
        if (responseType != null || typeReference != null) {
            Object data = response.getBody();
            data = getBody(data);
            if (data instanceof String) {
                try {
                    if (responseType != null) {
                        data = getObjectMapper().readValue((String) data, responseType);
                    } else {
                        data = getObjectMapper().readValue((String) data, typeReference);
                    }
                    response.setBody(data);
                } catch (JacksonException ex) {
                    throw new IllegalArgumentException(ex);
                }
            }
        }
        return response;
    }
}
