package org.tenio.interstellar.context;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class Utils {
    public static final java.util.Base64.Encoder BASE64_ENCODER;
    public static final java.util.Base64.Decoder BASE64_DECODER;

    static {
        BASE64_ENCODER = java.util.Base64.getUrlEncoder().withoutPadding();
        BASE64_DECODER = java.util.Base64.getUrlDecoder();
    }

    public static Object wrapJsonValue(Object val) {
        if (val == null) {
            return null;
        }

        // perform wrapping
        if (val instanceof Map) {
            val = new DataObject((Map) val);
        } else if (val instanceof List) {
            val = new DataArray((List) val);
        } else if (val instanceof Instant) {
            val = ISO_INSTANT.format((Instant) val);
        } else if (val instanceof byte[]) {
            val = BASE64_ENCODER.encodeToString((byte[]) val);
        } else if (val instanceof Enum) {
            val = ((Enum) val).name();
        }

        return val;
    }


    public static Object checkAndCopy(Object val) {
        if (val == null) {
            // OK
        } else if (val instanceof Number) {
            // OK
        } else if (val instanceof Boolean) {
            // OK
        } else if (val instanceof String) {
            // OK
        } else if (val instanceof Character) {
            // OK
        } else if (val instanceof CharSequence) {
            // CharSequences are not immutable, so we force toString() to become immutable
            val = val.toString();
        } else if (val instanceof Map) {
            val = (new DataObject((Map) val)).copy();
        } else if (val instanceof List) {
            val = (new DataArray((List) val)).copy();
        } else if (val instanceof byte[]) {
            // OK
        } else if (val instanceof Instant) {
            // OK
        } else if (val instanceof Enum) {
            // OK
        } else {
            throw new IllegalStateException("Illegal type in Json: " + val.getClass());
        }
        return val;
    }
}
