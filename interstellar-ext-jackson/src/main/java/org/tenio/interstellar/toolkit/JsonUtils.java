package org.tenio.interstellar.toolkit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tenio.interstellar.jackson.ObjectMapperFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author sunkaihan
 * @date 2022-04-26
 */
public class JsonUtils {
    private final static ObjectMapper objectMapper = ObjectMapperFactory.objectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception err) {
            return null;
        }
    }

    public static JsonNode fromJson(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(JsonNode jsonNode, Class<T> type) throws IOException {
        return objectMapper.treeToValue(jsonNode, type);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T nodeTo(JsonNode json, TypeReference<T> typeReference) {
        try {
            return objectMapper.convertValue(json, typeReference);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> nodeToList(JsonNode json, Class<T> clazz) {
        try {
            return objectMapper.readerForListOf(clazz).readValue(json);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        try {
            return objectMapper.readerForListOf(clazz).readValue(json);
        } catch (Exception e) {
            return null;
        }
    }

    public static final TypeReference<Map<String, Object>> MAP_STRING_OBJECT_TYPE_REFERENCE = new TypeReference<>() {
    };

    public static Map<String, Object> fromJsonToMap(String json) {

        try {
            return objectMapper.readValue(json, MAP_STRING_OBJECT_TYPE_REFERENCE);
        } catch (IOException e) {
            return null;
        }
    }
}
