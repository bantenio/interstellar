package org.tenio.interstellar.jackson;

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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author sunkaihan
 * @date 2022-04-26
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

    public static final String COMMON_OBJECT_MAPPER_BUILDER = "commonObjectMapperBuilder";
    public static final String IGNORE_NULL_OBJECT_MAPPER_BUILDER = "ignoreNullObjectMapperBuilder";

    private static final Map<String, Consumer<ObjectMapper>> objectMapperBuilders = new ConcurrentHashMap<>();

    static {
        registerObjectMapperBuilder(COMMON_OBJECT_MAPPER_BUILDER, ObjectMapperFactory::buildCommonObjectMapper);
        registerObjectMapperBuilder(IGNORE_NULL_OBJECT_MAPPER_BUILDER, ObjectMapperFactory::buildIgnoreNullObjectMapper);
    }

    public static void registerObjectMapperBuilder(String key, Consumer<ObjectMapper> builder) {
        objectMapperBuilders.put(key, builder);
    }

    public static void simpleModule(ObjectMapper objectMapper) {
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

    public static void javaTimeModule(ObjectMapper objectMapper) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
    }

    public static void disableWriteTimestamps(ObjectMapper objectMapper) {
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
    }

    public static void ignoreNull(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper buildCommonObjectMapper(ObjectMapper objectMapper) {
        simpleModule(objectMapper);
        javaTimeModule(objectMapper);
        disableWriteTimestamps(objectMapper);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        objectMapper.registerModule(new ParameterNamesModule());

        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new NullBeanSerializerModifier()));
        return objectMapper;
    }

    public static ObjectMapper buildIgnoreNullObjectMapper(ObjectMapper objectMapper) {
        buildCommonObjectMapper(objectMapper);
        ignoreNull(objectMapper);
        return objectMapper;
    }

    public static ObjectMapper objectMapper(String key) {
        if (!objectMapperBuilders.containsKey(key)) {
            throw new IllegalStateException(CharSequenceUtil.format("The {} ObjectMapper builder is not register.", key));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapperBuilders.get(key).accept(objectMapper);
        return objectMapper;
    }

    public static ObjectMapper objectMapper() {
        return objectMapper(COMMON_OBJECT_MAPPER_BUILDER);
    }

    public static ObjectMapper ignoreNullObjectMapper() {
        return objectMapper(IGNORE_NULL_OBJECT_MAPPER_BUILDER);
    }
}
