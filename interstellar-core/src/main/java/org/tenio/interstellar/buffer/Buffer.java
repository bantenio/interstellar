package org.tenio.interstellar.buffer;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @author sunkaihan
 * @date 2022-04-18
 */
public interface Buffer {


    static Buffer buffer() {
        return BufferImpl.buffer();
    }

    static Buffer buffer(int initialSizeHint) {
        return BufferImpl.buffer(initialSizeHint);
    }

    static Buffer buffer(String string) {
        return BufferImpl.buffer(string);
    }

    static Buffer buffer(String string, String enc) {
        return BufferImpl.buffer(string, enc);
    }

    static Buffer buffer(byte[] bytes) {
        return BufferImpl.buffer(bytes);
    }

    static Buffer buffer(ByteBuf byteBuf) {
        Objects.requireNonNull(byteBuf);
        return BufferImpl.buffer(byteBuf);
    }

    String toString();

    String toString(String enc);

    String toString(Charset enc);

    byte getByte(int pos);

    short getUnsignedByte(int pos);

    int getInt(int pos);

    int getIntLE(int pos);

    long getUnsignedInt(int pos);

    long getUnsignedIntLE(int pos);

    long getLong(int pos);

    long getLongLE(int pos);

    double getDouble(int pos);

    float getFloat(int pos);

    short getShort(int pos);

    short getShortLE(int pos);

    int getUnsignedShort(int pos);

    int getUnsignedShortLE(int pos);

    int getMedium(int pos);

    int getMediumLE(int pos);

    int getUnsignedMedium(int pos);

    int getUnsignedMediumLE(int pos);

    byte[] getBytes();

    byte[] getBytes(int start, int end);

    Buffer getBytes(byte[] dst);

    Buffer getBytes(byte[] dst, int dstIndex);

    Buffer getBytes(int start, int end, byte[] dst);

    Buffer getBytes(int start, int end, byte[] dst, int dstIndex);

    Buffer getBuffer(int start, int end);

    String getString(int start, int end, String enc);

    String getString(int start, int end);

    Buffer appendBuffer(Buffer buff);

    Buffer appendBuffer(Buffer buff, int offset, int len);

    Buffer appendBytes(byte[] bytes);

    Buffer appendBytes(byte[] bytes, int offset, int len);

    Buffer appendByte(byte b);

    Buffer appendUnsignedByte(short b);

    Buffer appendInt(int i);

    Buffer appendIntLE(int i);

    Buffer appendUnsignedInt(long i);

    Buffer appendUnsignedIntLE(long i);

    Buffer appendMedium(int i);

    Buffer appendMediumLE(int i);

    Buffer appendLong(long l);

    Buffer appendLongLE(long l);

    Buffer appendShort(short s);

    Buffer appendShortLE(short s);

    Buffer appendUnsignedShort(int s);

    Buffer appendUnsignedShortLE(int s);

    Buffer appendFloat(float f);

    Buffer appendDouble(double d);

    Buffer appendString(String str, String enc);

    Buffer appendString(String str);

    Buffer setByte(int pos, byte b);

    Buffer setUnsignedByte(int pos, short b);

    Buffer setInt(int pos, int i);

    Buffer setIntLE(int pos, int i);

    Buffer setUnsignedInt(int pos, long i);

    Buffer setUnsignedIntLE(int pos, long i);

    Buffer setMedium(int pos, int i);

    Buffer setMediumLE(int pos, int i);

    Buffer setLong(int pos, long l);

    Buffer setLongLE(int pos, long l);

    Buffer setDouble(int pos, double d);

    Buffer setFloat(int pos, float f);

    Buffer setShort(int pos, short s);

    Buffer setShortLE(int pos, short s);

    Buffer setUnsignedShort(int pos, int s);

    Buffer setUnsignedShortLE(int pos, int s);

    Buffer setBuffer(int pos, Buffer b);

    Buffer setBuffer(int pos, Buffer b, int offset, int len);

    Buffer setBytes(int pos, ByteBuffer b);

    Buffer setBytes(int pos, byte[] b);

    Buffer setBytes(int pos, byte[] b, int offset, int len);

    Buffer setString(int pos, String str);

    Buffer setString(int pos, String str, String enc);

    int length();

    Buffer copy();

    Buffer slice();

    Buffer slice(int start, int end);

    ByteBuf getByteBuf();
}
