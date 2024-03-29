package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.toolkit.JsonUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: JsonResponseInterceptor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 16:00
 * &#064;version: 1.0
 */
public class JsonResponseInterceptor extends ResponseInterceptor implements JsonResponseHandler {
    /**
     * TODO
     */
    private final ObjectMapper objectMapper;
    /**
     * TODO
     */
    private final Charset charset;
    private Class<?> responseType;
    private TypeReference<?> typeReference;

    /**
     * TODO
     */
    public JsonResponseInterceptor() {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), StandardCharsets.UTF_8);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public JsonResponseInterceptor(ObjectMapper objectMapper) {
        this(objectMapper, StandardCharsets.UTF_8);
    }

    /**
     * TODO
     *
     * @param charset TODO
     */
    public JsonResponseInterceptor(Charset charset) {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), charset);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     * @param charset      TODO
     */
    public JsonResponseInterceptor(ObjectMapper objectMapper, Charset charset) {
        this.objectMapper = objectMapper;
        this.charset = charset;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    @Override
    public Class<?> getResponseType() {
        return responseType;
    }

    /**
     * TODO
     *
     * @param responseType TODO
     * @return TODO
     */
    public JsonResponseInterceptor setResponseType(Class<?> responseType) {
        this.responseType = responseType;
        return this;
    }

    /**
     * @return TODO
     */
    @Override
    public TypeReference<?> getTypeReference() {
        return typeReference;
    }

    /**
     * TODO
     *
     * @param typeReference TODO
     * @return TODO
     */
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
