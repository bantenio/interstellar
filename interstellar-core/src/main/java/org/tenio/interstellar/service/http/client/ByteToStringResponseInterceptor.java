package org.tenio.interstellar.service.http.client;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: ByteToStringRequestInterceptor
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 15:31
 * &#064;version: 1.0
 */
public class ByteToStringResponseInterceptor extends ResponseInterceptor {
    private final Charset codeCharset;

    /**
     * 使用默认UTF-8编码
     */
    public ByteToStringResponseInterceptor() {
        this(StandardCharsets.UTF_8);
    }

    /**
     * 指定编码
     *
     * @param codeCharset 内容编码
     */
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
