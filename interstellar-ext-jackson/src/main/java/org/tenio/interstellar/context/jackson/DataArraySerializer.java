package org.tenio.interstellar.context.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.tenio.interstellar.context.DataArray;

import java.io.IOException;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class DataArraySerializer extends JsonSerializer<DataArray> {
    @Override
    public void serialize(DataArray value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeObject(value.getList());
    }
}

