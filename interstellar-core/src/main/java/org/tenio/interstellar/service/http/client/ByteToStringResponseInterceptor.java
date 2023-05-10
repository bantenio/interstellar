package org.tenio.interstellar.service.http.client;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: ByteToStringRequestInterceptor
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 15:31
 * @version: 1.0
 */
public class ByteToStringResponseInterceptor extends ResponseInterceptor {
    private final Charset codeCharset;

    public ByteToStringResponseInterceptor() {
        this(StandardCharsets.UTF_8);
    }

    public ByteToStringResponseInterceptor(Charset codeCharset) {
        this.codeCharset = codeCharset;
    }

    @Override
    protected Response afterRequest(Response response, RequestBuilder<? extends RequestBuilder<?>> requestBuilder) {
        Object data = response.getBody();
        byte[] buf = null;
        if (data instanceof byte[]) {
            buf = (byte[]) data;
        } else if (data instanceof ByteBuffer) {
            buf = ((ByteBuffer) data).array();
        } else if (data instanceof ByteBuf) {
            buf = ((ByteBuf) data).array();
        }
        if (buf != null) {
            response.setBody(new String(buf, codeCharset));
        }
        return response;
    }
}
