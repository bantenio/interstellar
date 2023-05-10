package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.toolkit.JsonUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: JsonResponseInterceptor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 16:00
 * @version: 1.0
 */
public class JsonResponseInterceptor extends ResponseInterceptor implements JsonResponseHandler {
    private final ObjectMapper objectMapper;
    private final Charset charset;
    private Class<?> responseType;
    private TypeReference<?> typeReference;

    public JsonResponseInterceptor() {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), StandardCharsets.UTF_8);
    }

    public JsonResponseInterceptor(ObjectMapper objectMapper) {
        this(objectMapper, StandardCharsets.UTF_8);
    }

    public JsonResponseInterceptor(Charset charset) {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), charset);
    }

    public JsonResponseInterceptor(ObjectMapper objectMapper, Charset charset) {
        this.objectMapper = objectMapper;
        this.charset = charset;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    public Class<?> getResponseType() {
        return responseType;
    }

    public JsonResponseInterceptor setResponseType(Class<?> responseType) {
        this.responseType = responseType;
        return this;
    }

    public TypeReference<?> getTypeReference() {
        return typeReference;
    }

    public JsonResponseInterceptor setTypeReference(TypeReference<?> typeReference) {
        this.typeReference = typeReference;
        return this;
    }

    @Override
    protected Response afterRequest(Response response, RequestBuilder<? extends RequestBuilder<?>> requestBuilder) {
        return afterHandle(response, requestBuilder);
    }

    @Override
    public Charset getCharset() {
        return charset;
    }
}
