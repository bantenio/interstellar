package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.toolkit.JsonUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: JsonInterceptor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 15:40
 * &#064;version: 1.0
 */
public class JsonInterceptorRequest implements Interceptor, JsonRequestHandler, JsonResponseHandler {
    private final ObjectMapper objectMapper;
    private final Charset charset;
    private boolean withContentType = true;
    private Class<?> responseType;
    private TypeReference<?> typeReference;

    /**
     * TODO
     */
    public JsonInterceptorRequest() {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), StandardCharsets.UTF_8);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public JsonInterceptorRequest(ObjectMapper objectMapper) {
        this(objectMapper, StandardCharsets.UTF_8);
    }

    /**
     * TODO
     *
     * @param charset TODO
     */
    public JsonInterceptorRequest(Charset charset) {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), charset);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     * @param charset      TODO
     */
    public JsonInterceptorRequest(ObjectMapper objectMapper, Charset charset) {
        this.objectMapper = objectMapper;
        this.charset = charset;
    }

    /**
     * @return TODO
     */
    public Class<?> getResponseType() {
        return responseType;
    }

    /**
     * TODO
     *
     * @param responseType TODO
     * @return TODO
     */
    public JsonInterceptorRequest setResponseType(Class<?> responseType) {
        this.responseType = responseType;
        return this;
    }

    /**
     * @return TODO
     */
    public TypeReference<?> getTypeReference() {
        return typeReference;
    }

    /**
     * TODO
     *
     * @param typeReference TODO
     * @return TODO
     */
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
