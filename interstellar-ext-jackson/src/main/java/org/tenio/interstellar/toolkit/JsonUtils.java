package org.tenio.interstellar.toolkit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.context.Utils;
import org.tenio.interstellar.jackson.ObjectMapperFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static org.tenio.interstellar.jackson.ObjectMapperFactory.COMMON_OBJECT_MAPPER_BUILDER;
import static org.tenio.interstellar.jackson.ObjectMapperFactory.IGNORE_NULL_OBJECT_MAPPER_BUILDER;

/**
 * &#064;author sunkaihan
 */
public class JsonUtils {

    private static final Map<String, ObjectMapper> objectMapperCache = new ConcurrentHashMap<>();
    public static final String COMMON_OBJECT_MAPPER = COMMON_OBJECT_MAPPER_BUILDER;
    public static final String IGNORE_NULL_OBJECT_MAPPER = IGNORE_NULL_OBJECT_MAPPER_BUILDER;


    public static ObjectMapper getOrCreate(String key, Function<String, ObjectMapper> objectMapperFunction) {
        return objectMapperCache.computeIfAbsent(key, objectMapperFunction);
    }

    public static final Function<Object, ?> JSON_NODE_CLONER = o -> {
        if (o instanceof JsonNode) {
            return ((JsonNode) o).deepCopy();
        }
        return Utils.DEFAULT_CLONER.apply(o);
    };

    private static ObjectMapper getOrCreate(String key) {
        return getOrCreate(key, ObjectMapperFactory::objectMapper);
    }

    public static String toJson(Object obj) {
        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).writeValueAsString(obj);
        } catch (Exception err) {
            return null;
        }
    }

    // region fromXxx functions
    public static JsonNode fromJson(String json) {
        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).readTree(json);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(JsonNode jsonNode, Class<T> type) throws IOException {
        return getOrCreate(COMMON_OBJECT_MAPPER).treeToValue(jsonNode, type);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).readValue(json, type);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).readValue(json, typeReference);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).readerForListOf(clazz).readValue(json);
        } catch (Exception e) {
            return null;
        }
    }

    public static final TypeReference<Map<String, Object>> MAP_STRING_OBJECT_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {
    };

    public static Map<String, Object> fromJsonToMap(String json) {

        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).readValue(json, MAP_STRING_OBJECT_TYPE_REFERENCE);
        } catch (IOException e) {
            return null;
        }
    }
    // endregion

    public static JsonNode valueToJsonNode(Object value) {
        return getOrCreate(COMMON_OBJECT_MAPPER).valueToTree(value);
    }

    public static <T> T nodeTo(JsonNode json, TypeReference<T> typeReference) {
        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).convertValue(json, typeReference);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> nodeToList(JsonNode json, Class<T> clazz) {
        try {
            return getOrCreate(COMMON_OBJECT_MAPPER).readerForListOf(clazz).readValue(json);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T mapTo(Map from, Class<T> clazz) {
        return getOrCreate(COMMON_OBJECT_MAPPER).convertValue(from, clazz);
    }

    public static Map<String, Object> mapFrom(Object from) {
        return getOrCreate(COMMON_OBJECT_MAPPER).convertValue(from, MAP_STRING_OBJECT_TYPE_REFERENCE);
    }
}
