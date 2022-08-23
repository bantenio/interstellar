package org.tenio.interstellar.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.tenio.interstellar.context.Utils;

import java.io.IOException;

public class ByteArraySerializer extends JsonSerializer<byte[]> {

    @Override
    public void serialize(byte[] value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(Utils.BASE64_ENCODER.encodeToString(value));
    }
}
