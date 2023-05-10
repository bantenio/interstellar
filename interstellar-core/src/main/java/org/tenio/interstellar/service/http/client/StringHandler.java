package org.tenio.interstellar.service.http.client;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @projectName: interstellar
 * @package: org.tenio.interstellar.service.http.client
 * @className: StringBodyHandler
 * @author: Ban Tenio
 * @description: TODO
 * @date: 2023/5/10 16:11
 * @version: 1.0
 */
public interface StringHandler {
    Charset getCharset();

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
