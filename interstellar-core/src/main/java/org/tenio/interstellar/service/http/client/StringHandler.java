package org.tenio.interstellar.service.http.client;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * &#064;projectName: interstellar
 * &#064;package: org.tenio.interstellar.service.http.client
 * &#064;className: StringBodyHandler
 * &#064;author: Ban Tenio
 * &#064;description: TODO
 * &#064;date: 2023/5/10 16:11
 * &#064;version: 1.0
 */
public interface StringHandler {
    /**
     * TODO
     *
     * @return TODO
     */
    Charset getCharset();

    /**
     * TODO
     *
     * @param data TODO
     * @return TODO
     */
    default Object getBody(Object data) {
        if (data instanceof CharSequence) {
            return data.toString();
        }
        byte[] buf = null;
        if (data instanceof byte[]) {
            buf = (byte[]) data;
        } else if (data instanceof ByteBuffer) {
            buf = ((ByteBuffer) data).array();
        } else if (data instanceof ByteBuf) {
            buf = ((ByteBuf) data).array();
        }
        return buf == null ? data : new String(buf, getCharset());
    }
}
