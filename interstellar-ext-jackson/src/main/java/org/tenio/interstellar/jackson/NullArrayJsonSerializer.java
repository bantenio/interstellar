package org.tenio.interstellar.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
/**
 * @author sunkaihan
 * @date 2022-04-26
 */
public class NullArrayJsonSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        if (value == null) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeEndArray();
        }

    }

}
