package org.tenio.interstellar.buffer;

import io.netty.buffer.AbstractByteBufAllocator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;

public class HeapByteBufAllocator extends AbstractByteBufAllocator {
    public static final HeapByteBufAllocator DEFAULT = new HeapByteBufAllocator();
    @Override
    protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
        return new HeapByteBuf(this, initialCapacity, maxCapacity);
    }

    @Override
    protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
        return UnpooledByteBufAllocator.DEFAULT.directBuffer(initialCapacity, maxCapacity);
    }

    @Override
    public boolean isDirectBufferPooled() {
        return false;
    }
}
