package org.tenio.interstellar.context.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.tenio.interstellar.context.DataObject;

import java.io.IOException;
import java.util.Map;

public class DataObjectDeserializer extends JsonDeserializer<DataObject> {

    public static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<Map<String, Object>>() {
    };

    @Override
    public DataObject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, Object> value = p.readValueAs(MAP_TYPE);
        try {
            return new DataObject(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException(p, "Expected a JSON Struct", value, DataObject.class);
        }
    }
}
