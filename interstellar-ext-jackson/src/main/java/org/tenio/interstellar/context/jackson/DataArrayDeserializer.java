package org.tenio.interstellar.context.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.tenio.interstellar.context.DataArray;

import java.io.IOException;
import java.util.List;

public class DataArrayDeserializer extends JsonDeserializer<DataArray> {

    public static final TypeReference<List<Object>> MAP_TYPE = new TypeReference<List<Object>>() {
    };

    @Override
    public DataArray deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        List<Object> value = p.readValueAs(MAP_TYPE);
        try {
            return new DataArray(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException(p, "Expected a JSON List", value, DataArray.class);
        }
    }
}
