package org.tenio.interstellar.service.http.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.toolkit.JsonUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: JsonRequestInterceptor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 15:59
 * &#064;version: 1.0
 */
public class JsonRequestInterceptor extends RequestInterceptor implements JsonRequestHandler {
    /**
     * TODO
     */
    private final ObjectMapper objectMapper;
    /**
     * TODO
     */
    private final Charset charset;
    private boolean withContentType = true;

    /**
     * TODO
     */
    public JsonRequestInterceptor() {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), StandardCharsets.UTF_8);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public JsonRequestInterceptor(ObjectMapper objectMapper) {
        this(objectMapper, StandardCharsets.UTF_8);
    }

    /**
     * TODO
     *
     * @param charset TODO
     */
    public JsonRequestInterceptor(Charset charset) {
        this(JsonUtils.getOrCreate(JsonUtils.COMMON_OBJECT_MAPPER), charset);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     * @param charset      TODO
     */
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
