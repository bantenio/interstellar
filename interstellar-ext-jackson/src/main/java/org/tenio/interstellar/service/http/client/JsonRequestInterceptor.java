package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.toolkit.JsonUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: JsonRequestInterceptor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 15:59
 * @version: 1.0
 */
public class JsonRequestInterceptor extends RequestInterceptor implements JsonRequestHandler {
    private final ObjectMapper objectMapper;
    private final Charset charset;
    private boolean withContentType = true;

    public JsonRequestInterceptor() {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), StandardCharsets.UTF_8);
    }

    public JsonRequestInterceptor(ObjectMapper objectMapper) {
        this(objectMapper, StandardCharsets.UTF_8);
    }

    public JsonRequestInterceptor(Charset charset) {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), charset);
    }

    public JsonRequestInterceptor(ObjectMapper objectMapper, Charset charset) {
        this.objectMapper = objectMapper;
        this.charset = charset;
    }

    @Override
    protected void preRequest(RequestBuilder<? extends RequestBuilder<?>> requestBuilder) {
        preHandle(requestBuilder);
    }

    @Override
    public Charset getCharset() {
        return this.charset;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }

    @Override
    public boolean withContentType() {
        return this.withContentType;
    }
}
