package org.tenio.interstellar.jackson;

import cn.hutool.core.map.MapBuilder;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.tenio.interstellar.buffer.Buffer;
import org.tenio.interstellar.buffer.jackson.BufferDeserializer;
import org.tenio.interstellar.buffer.jackson.BufferSerializer;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;
import org.tenio.interstellar.context.jackson.DataArrayDeserializer;
import org.tenio.interstellar.context.jackson.DataArraySerializer;
import org.tenio.interstellar.context.jackson.DataObjectDeserializer;
import org.tenio.interstellar.context.jackson.DataObjectSerializer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static com.fasterxml.jackson.core.JsonParser.Feature.*;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class ObjectMapperFactory {
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    /**
     * TODO
     */
    public static final String COMMON_OBJECT_MAPPER_BUILDER = "commonObjectMapperBuilder";
    /**
     * TODO
     */
    public static final String IGNORE_NULL_OBJECT_MAPPER_BUILDER = "ignoreNullObjectMapperBuilder";
    ;
    /**
     * TODO
     */
    public static final String JSON5_OBJECT_MAPPER_BUILDER = "ignoreNullObjectMapperBuilder";

    /**
     * TODO
     */
    public static final List<String> COMMONS_OBJECT_MAPPER = Arrays.asList("extendTypes",
            "javaTime",
            "disableWriteTimestamps",
            "parameterNames",
            "withNullSerializer",
            "closeUnknownFieldFail");

    /**
     * TODO
     */
    public static final List<String> IGNORE_NULL_OBJECT_MAPPER = Arrays.asList("extendTypes",
            "javaTime",
            "disableWriteTimestamps",
            "parameterNames",
            "withNullSerializer",
            "closeUnknownFieldFail",
            "ignoreNull");
    /**
     * TODO
     */
    public static final List<String> JSON5_OBJECT_MAPPER = Arrays.asList("extendTypes",
            "javaTime",
            "disableWriteTimestamps",
            "parameterNames",
            "withNullSerializer",
            "closeUnknownFieldFail",
            "json5");
    /**
     * TODO
     */
    public static final Map<String, List<String>> PUBLISH_OBJECT_MAPPER_HANDLE_LISTS =
            MapBuilder.create(new HashMap<String, List<String>>())
                    .put(COMMON_OBJECT_MAPPER_BUILDER, COMMONS_OBJECT_MAPPER)
                    .put(IGNORE_NULL_OBJECT_MAPPER_BUILDER, IGNORE_NULL_OBJECT_MAPPER)
                    .put(JSON5_OBJECT_MAPPER_BUILDER, JSON5_OBJECT_MAPPER)
                    .build();

    private static final Map<String, Consumer<ObjectMapper>> OBJECT_MAPPER_HANDLER = new ConcurrentHashMap<>();

    static {
        OBJECT_MAPPER_HANDLER.put("extendTypes", ObjectMapperFactory::extendTypes);
        OBJECT_MAPPER_HANDLER.put("json5", ObjectMapperFactory::json5);
        OBJECT_MAPPER_HANDLER.put("javaTime", ObjectMapperFactory::javaTime);
        OBJECT_MAPPER_HANDLER.put("disableWriteTimestamps", ObjectMapperFactory::disableWriteTimestamps);
        OBJECT_MAPPER_HANDLER.put("ignoreNull", ObjectMapperFactory::ignoreNull);
        OBJECT_MAPPER_HANDLER.put("parameterNames", ObjectMapperFactory::parameterNames);
        OBJECT_MAPPER_HANDLER.put("withNullSerializer", ObjectMapperFactory::withNullSerializer);
        OBJECT_MAPPER_HANDLER.put("closeUnknownFieldFail", ObjectMapperFactory::closeUnknownFieldFail);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void extendTypes(ObjectMapper objectMapper) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(DataObject.class, new DataObjectSerializer());
        simpleModule.addDeserializer(DataObject.class, new DataObjectDeserializer());
        simpleModule.addSerializer(DataArray.class, new DataArraySerializer());
        simpleModule.addDeserializer(DataArray.class, new DataArrayDeserializer());
        simpleModule.addSerializer(Buffer.class, new BufferSerializer());
        simpleModule.addDeserializer(Buffer.class, new BufferDeserializer());
        simpleModule.addSerializer(byte[].class, new ByteArraySerializer());
        simpleModule.addDeserializer(byte[].class, new ByteArrayDeserializer());
        simpleModule.addSerializer(Instant.class, new InstantSerializer());
        simpleModule.addDeserializer(Instant.class, new InstantDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void json5(ObjectMapper objectMapper) {
        objectMapper.enable(ALLOW_UNQUOTED_FIELD_NAMES,
                ALLOW_TRAILING_COMMA,
                ALLOW_SINGLE_QUOTES,
                ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER,
                ALLOW_NON_NUMERIC_NUMBERS,
                ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS,
                ALLOW_COMMENTS);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void javaTime(ObjectMapper objectMapper) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void disableWriteTimestamps(ObjectMapper objectMapper) {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void ignoreNull(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void parameterNames(ObjectMapper objectMapper) {
        objectMapper.registerModule(new ParameterNamesModule());
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void withNullSerializer(ObjectMapper objectMapper) {
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new NullBeanSerializerModifier()));
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     */
    public static void closeUnknownFieldFail(ObjectMapper objectMapper) {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * TODO
     *
     * @param objectMapper TODO
     * @param handlers     TODO
     * @return TODO
     */
    public static ObjectMapper buildWithHandlers(ObjectMapper objectMapper, List<String> handlers) {
        for (String handler : handlers) {
            OBJECT_MAPPER_HANDLER.get(handler).accept(objectMapper);
        }
        return objectMapper;
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public static ObjectMapper objectMapper(String key) {
        if (!PUBLISH_OBJECT_MAPPER_HANDLE_LISTS.containsKey(key)) {
            throw new IllegalStateException(CharSequenceUtil.format("The {} ObjectMapper builder is not register.", key));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return buildWithHandlers(objectMapper, PUBLISH_OBJECT_MAPPER_HANDLE_LISTS.get(key));
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public static ObjectMapper objectMapper() {
        return commonObjectMapper();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public static ObjectMapper commonObjectMapper() {
        return objectMapper(COMMON_OBJECT_MAPPER_BUILDER);
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public static ObjectMapper ignoreNullObjectMapper() {
        return objectMapper(IGNORE_NULL_OBJECT_MAPPER_BUILDER);
    }
}
