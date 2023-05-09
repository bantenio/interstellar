package org.tenio.interstellar.buffer.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.tenio.interstellar.buffer.Buffer;
import org.tenio.interstellar.context.Utils;

import java.io.IOException;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class BufferSerializer extends JsonSerializer<Buffer> {

    @Override
    public void serialize(Buffer value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(Utils.BASE64_ENCODER.encodeToString(value.getBytes()));
    }
}
