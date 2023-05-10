package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.toolkit.JsonUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: JsonInterceptor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 15:40
 * @version: 1.0
 */
public class JsonInterceptorRequest implements Interceptor, JsonRequestHandler, JsonResponseHandler {
    private final ObjectMapper objectMapper;
    private final Charset charset;
    private boolean withContentType = true;
    private Class<?> responseType;
    private TypeReference<?> typeReference;

    public JsonInterceptorRequest() {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), StandardCharsets.UTF_8);
    }

    public JsonInterceptorRequest(ObjectMapper objectMapper) {
        this(objectMapper, StandardCharsets.UTF_8);
    }

    public JsonInterceptorRequest(Charset charset) {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), charset);
    }

    public JsonInterceptorRequest(ObjectMapper objectMapper, Charset charset) {
        this.objectMapper = objectMapper;
        this.charset = charset;
    }

    public Class<?> getResponseType() {
        return responseType;
    }

    public JsonInterceptorRequest setResponseType(Class<?> responseType) {
        this.responseType = responseType;
        return this;
    }

    public TypeReference<?> getTypeReference() {
        return typeReference;
    }

    public JsonInterceptorRequest setTypeReference(TypeReference<?> typeReference) {
        this.typeReference = typeReference;
        return this;
    }

    @Override
    public Response handle(RequestBuilder<? extends RequestBuilder<?>> requestBuilder, SimpleInterceptorChain chain) {
        preHandle(requestBuilder);
        Response response = chain.next(requestBuilder);
        return afterHandle(response, requestBuilder);
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    @Override
    public Charset getCharset() {
        return this.charset;
    }

    @Override
    public boolean withContentType() {
        return this.withContentType;
    }
}
