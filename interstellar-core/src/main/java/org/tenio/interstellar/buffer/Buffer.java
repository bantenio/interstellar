package org.tenio.interstellar.buffer;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * &#064;author sunkaihan
 */
public interface Buffer {

    /**
     * TODO
     *
     * @return TODO
     */
    static Buffer buffer() {
        return BufferImpl.buffer();
    }

    /**
     * TODO
     *
     * @param initialSizeHint TODO
     * @return TODO
     */
    static Buffer buffer(int initialSizeHint) {
        return BufferImpl.buffer(initialSizeHint);
    }

    /**
     * TODO
     *
     * @param string TODO
     * @return TODO
     */
    static Buffer buffer(String string) {
        return BufferImpl.buffer(string);
    }

    /**
     * @param string TODO
     * @param enc    TODO
     * @return TODO
     */
    static Buffer buffer(String string, String enc) {
        return BufferImpl.buffer(string, enc);
    }

    /**
     * TODO
     *
     * @param bytes TODO
     * @return TODO
     */
    static Buffer buffer(byte[] bytes) {
        return BufferImpl.buffer(bytes);
    }

    /**
     * TODO
     *
     * @param byteBuf TODO
     * @return TODO
     */
    static Buffer buffer(ByteBuf byteBuf) {
        Objects.requireNonNull(byteBuf);
        return BufferImpl.buffer(byteBuf);
    }

    /**
     * TODO
     *
     * @return TODO
     */
    String toString();

    /**
     * TODO
     *
     * @param enc TODO
     * @return TODO
     */
    String toString(String enc);

    /**
     * TODO
     *
     * @param enc TODO
     * @return TODO
     */
    String toString(Charset enc);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    byte getByte(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    short getUnsignedByte(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getInt(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getIntLE(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    long getUnsignedInt(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    long getUnsignedIntLE(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    long getLong(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    long getLongLE(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    double getDouble(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    float getFloat(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    short getShort(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    short getShortLE(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getUnsignedShort(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getUnsignedShortLE(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getMedium(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getMediumLE(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getUnsignedMedium(int pos);

    /**
     * TODO
     *
     * @param pos TODO
     * @return TODO
     */
    int getUnsignedMediumLE(int pos);

    /**
     * TODO
     *
     * @return TODO
     */
    byte[] getBytes();

    /**
     * TODO
     *
     * @param start TODO
     * @param end   TODO
     * @return TODO
     */
    byte[] getBytes(int start, int end);

    /**
     * TODO
     *
     * @param dst TODO
     * @return TODO
     */
    Buffer getBytes(byte[] dst);

    /**
     * TODO
     *
     * @param dst      TODO
     * @param dstIndex TODO
     * @return TODO
     */
    Buffer getBytes(byte[] dst, int dstIndex);

    /**
     * TODO
     *
     * @param start TODO
     * @param end   TODO
     * @param dst   TODO
     * @return TODO
     */
    Buffer getBytes(int start, int end, byte[] dst);

    /**
     * TODO
     *
     * @param start    TODO
     * @param end      TODO
     * @param dst      TODO
     * @param dstIndex TODO
     * @return TODO
     */
    Buffer getBytes(int start, int end, byte[] dst, int dstIndex);

    /**
     * TODO
     *
     * @param start TODO
     * @param end   TODO
     * @return TODO
     */
    Buffer getBuffer(int start, int end);

    /**
     * TODO
     *
     * @param start TODO
     * @param end   TODO
     * @param enc   TODO
     * @return TODO
     */
    String getString(int start, int end, String enc);

    /**
     * TODO
     *
     * @param start TODO
     * @param end   TODO
     * @return TODO
     */
    String getString(int start, int end);

    /**
     * TODO
     *
     * @param buff TODO
     * @return TODO
     */
    Buffer appendBuffer(Buffer buff);

    /**
     * TODO
     *
     * @param buff   TODO
     * @param offset TODO
     * @param len    TODO
     * @return TODO
     */
    Buffer appendBuffer(Buffer buff, int offset, int len);

    /**
     * TODO
     *
     * @param bytes TODO
     * @return TODO
     */
    Buffer appendBytes(byte[] bytes);

    /**
     * TODO
     *
     * @param bytes  TODO
     * @param offset TODO
     * @param len    TODO
     * @return TODO
     */
    Buffer appendBytes(byte[] bytes, int offset, int len);

    /**
     * TODO
     *
     * @param b TODO
     * @return TODO
     */
    Buffer appendByte(byte b);

    /**
     * TODO
     *
     * @param b TODO
     * @return TODO
     */
    Buffer appendUnsignedByte(short b);

    /**
     * TODO
     *
     * @param i TODO
     * @return TODO
     */
    Buffer appendInt(int i);

    /**
     * TODO
     *
     * @param i TODO
     * @return TODO
     */
    Buffer appendIntLE(int i);

    /**
     * TODO
     *
     * @param i TODO
     * @return TODO
     */
    Buffer appendUnsignedInt(long i);

    /**
     * TODO
     *
     * @param i TODO
     * @return TODO
     */
    Buffer appendUnsignedIntLE(long i);

    /**
     * TODO
     *
     * @param i TODO
     * @return TODO
     */
    Buffer appendMedium(int i);

    /**
     * TODO
     *
     * @param i TODO
     * @return TODO
     */
    Buffer appendMediumLE(int i);

    /**
     * TODO
     *
     * @param l TODO
     * @return TODO
     */
    Buffer appendLong(long l);

    /**
     * TODO
     *
     * @param l TODO
     * @return TODO
     */
    Buffer appendLongLE(long l);

    /**
     * TODO
     *
     * @param s TODO
     * @return TODO
     */
    Buffer appendShort(short s);

    /**
     * TODO
     *
     * @param s TODO
     * @return TODO
     */
    Buffer appendShortLE(short s);

    /**
     * TODO
     *
     * @param s TODO
     * @return TODO
     */
    Buffer appendUnsignedShort(int s);

    /**
     * TODO
     *
     * @param s TODO
     * @return TODO
     */
    Buffer appendUnsignedShortLE(int s);

    /**
     * TODO
     *
     * @param f TODO
     * @return TODO
     */
    Buffer appendFloat(float f);

    /**
     * TODO
     *
     * @param d TODO
     * @return TODO
     */
    Buffer appendDouble(double d);

    /**
     * TODO
     *
     * @param str TODO
     * @param enc TODO
     * @return TODO
     */
    Buffer appendString(String str, String enc);

    /**
     * TODO
     *
     * @param str TODO
     * @return TODO
     */
    Buffer appendString(String str);

    /**
     * TODO
     *
     * @param pos TODO
     * @param b   TODO
     * @return TODO
     */
    Buffer setByte(int pos, byte b);

    /**
     * TODO
     *
     * @param pos TODO
     * @param b   TODO
     * @return TODO
     */
    Buffer setUnsignedByte(int pos, short b);

    /**
     * TODO
     *
     * @param pos TODO
     * @param i   TODO
     * @return TODO
     */
    Buffer setInt(int pos, int i);

    /**
     * TODO
     *
     * @param pos TODO
     * @param i   TODO
     * @return TODO
     */
    Buffer setIntLE(int pos, int i);

    /**
     * TODO
     *
     * @param pos TODO
     * @param i   TODO
     * @return TODO
     */
    Buffer setUnsignedInt(int pos, long i);

    /**
     * TODO
     *
     * @param pos TODO
     * @param i   TODO
     * @return TODO
     */
    Buffer setUnsignedIntLE(int pos, long i);

    /**
     * TODO
     *
     * @param pos TODO
     * @param i   TODO
     * @return TODO
     */
    Buffer setMedium(int pos, int i);

    /**
     * TODO
     *
     * @param pos TODO
     * @param i   TODO
     * @return TODO
     */
    Buffer setMediumLE(int pos, int i);

    /**
     * TODO
     *
     * @param pos TODO
     * @param l   TODO
     * @return TODO
     */
    Buffer setLong(int pos, long l);

    /**
     * TODO
     *
     * @param pos TODO
     * @param l   TODO
     * @return TODO
     */
    Buffer setLongLE(int pos, long l);

    /**
     * TODO
     *
     * @param pos TODO
     * @param d   TODO
     * @return TODO
     */
    Buffer setDouble(int pos, double d);

    /**
     * TODO
     *
     * @param pos TODO
     * @param f   TODO
     * @return TODO
     */
    Buffer setFloat(int pos, float f);

    /**
     * TODO
     *
     * @param pos TODO
     * @param s   TODO
     * @return TODO
     */
    Buffer setShort(int pos, short s);

    /**
     * TODO
     *
     * @param pos TODO
     * @param s   TODO
     * @return TODO
     */
    Buffer setShortLE(int pos, short s);

    /**
     * TODO
     *
     * @param pos TODO
     * @param s   TODO
     * @return TODO
     */
    Buffer setUnsignedShort(int pos, int s);

    /**
     * TODO
     *
     * @param pos TODO
     * @param s   TODO
     * @return TODO
     */
    Buffer setUnsignedShortLE(int pos, int s);

    /**
     * TODO
     *
     * @param pos TODO
     * @param b   TODO
     * @return TODO
     */
    Buffer setBuffer(int pos, Buffer b);

    /**
     * TODO
     *
     * @param pos    TODO
     * @param b      TODO
     * @param offset TODO
     * @param len    TODO
     * @return TODO
     */
    Buffer setBuffer(int pos, Buffer b, int offset, int len);

    /**
     * TODO
     *
     * @param pos TODO
     * @param b   TODO
     * @return TODO
     */
    Buffer setBytes(int pos, ByteBuffer b);

    /**
     * TODO
     *
     * @param pos TODO
     * @param b   TODO
     * @return TODO
     */
    Buffer setBytes(int pos, byte[] b);

    /**
     * TODO
     *
     * @param pos    TODO
     * @param b      TODO
     * @param offset TODO
     * @param len    TODO
     * @return TODO
     */
    Buffer setBytes(int pos, byte[] b, int offset, int len);

    /**
     * TODO
     *
     * @param pos TODO
     * @param str TODO
     * @return TODO
     */
    Buffer setString(int pos, String str);

    /**
     * TODO
     *
     * @param pos TODO
     * @param str TODO
     * @param enc TODO
     * @return TODO
     */
    Buffer setString(int pos, String str, String enc);

    /**
     * TODO
     *
     * @return TODO
     */
    int length();

    /**
     * TODO
     *
     * @return TODO
     */
    Buffer copy();

    /**
     * TODO
     *
     * @return TODO
     */
    Buffer slice();

    /**
     * TODO
     *
     * @param start TODO
     * @param end   TODO
     * @return TODO
     */
    Buffer slice(int start, int end);

    /**
     * TODO
     *
     * @return TODO
     */
    ByteBuf getByteBuf();
}
