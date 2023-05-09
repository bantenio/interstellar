package org.tenio.interstellar.context.mongo;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.tenio.interstellar.context.DataObject;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 *
 * @param <O> TODO
 * @param <A> TODO
 */
public abstract class AbstractDataCodec <O, A> implements Codec<O> {

    @Override
    public O decode(BsonReader reader, DecoderContext decoderContext) {
        return readDocument(reader, decoderContext);
    }

    @Override
    public void encode(BsonWriter writer, O value, EncoderContext encoderContext) {
        writeDocument(writer, null, value, encoderContext);
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readValue(BsonReader reader, DecoderContext ctx) {
        BsonType type = reader.getCurrentBsonType();
        switch (type) {
            case NULL:
                return readNull(reader, ctx);
            case ARRAY:
                return readArray(reader, ctx);
            case BINARY:
                return readBinary(reader, ctx);
            case BOOLEAN:
                return readBoolean(reader, ctx);
            case DATE_TIME:
                return readDateTime(reader, ctx);
            case DB_POINTER:
                return readDbPointer(reader, ctx);
            case DOCUMENT:
                return readDocument(reader, ctx);
            case DOUBLE:
                return readDouble(reader, ctx);
            case INT32:
                return readInt32(reader, ctx);
            case INT64:
                return readInt64(reader, ctx);
            case MAX_KEY:
                return readMaxKey(reader, ctx);
            case MIN_KEY:
                return readMinKey(reader, ctx);
            case JAVASCRIPT:
                return readJavaScript(reader, ctx);
            case JAVASCRIPT_WITH_SCOPE:
                return readJavaScriptWithScope(reader, ctx);
            case OBJECT_ID:
                return readObjectId(reader, ctx);
            case REGULAR_EXPRESSION:
                return readRegularExpression(reader, ctx);
            case STRING:
                return readString(reader, ctx);
            case SYMBOL:
                return readSymbol(reader, ctx);
            case TIMESTAMP:
                return readTimeStamp(reader, ctx);
            case UNDEFINED:
                return readUndefined(reader, ctx);
            case DECIMAL128:
                return readNumberDecimal(reader, ctx);
            default:
                throw new IllegalStateException("Unknown bson type " + type);
        }
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    @SuppressWarnings("unchecked")
    protected void writeValue(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        BsonType type = getBsonType(value);
        if (type == null) {
            throw new IllegalStateException("Unknown BsonType for '" + value + "'");
        }
        switch (type) {
            case NULL:
                writeNull(writer, name, value, ctx);
                break;
            case ARRAY:
                writeArray(writer, name, value, ctx);
                break;
            case BINARY:
                writeBinary(writer, name, value, ctx);
                break;
            case BOOLEAN:
                writeBoolean(writer, name, value, ctx);
                break;
            case DATE_TIME:
                writeDateTime(writer, name, value, ctx);
                break;
            case DB_POINTER:
                writeDbPointer(writer, name, value, ctx);
                break;
            case DOCUMENT:
                writeDocument(writer, name, value, ctx);
                break;
            case DOUBLE:
                writeDouble(writer, name, value, ctx);
                break;
            case INT32:
                writeInt32(writer, name, value, ctx);
                break;
            case INT64:
                writeInt64(writer, name, value, ctx);
                break;
            case DECIMAL128:
                writeNumberDecimal(writer, name, value, ctx);
                break;
            case MAX_KEY:
                writeMaxKey(writer, name, value, ctx);
                break;
            case MIN_KEY:
                writeMinKey(writer, name, value, ctx);
                break;
            case JAVASCRIPT:
                writeJavaScript(writer, name, value, ctx);
                break;
            case JAVASCRIPT_WITH_SCOPE:
                writeJavaScriptWithScope(writer, name, value, ctx);
                break;
            case OBJECT_ID:
                writeObjectId(writer, name, value, ctx);
                break;
            case REGULAR_EXPRESSION:
                writeRegularExpression(writer, name, value, ctx);
                break;
            case STRING:
                writeString(writer, name, value, ctx);
                break;
            case SYMBOL:
                writeSymbol(writer, name, value, ctx);
                break;
            case TIMESTAMP:
                writeTimeStamp(writer, name, value, ctx);
                break;
            case UNDEFINED:
                writeUndefined(writer, name, value, ctx);
                break;
            default:
                throw new IllegalStateException("Unknown bson type " + type);
        }
    }

    /**
     * TODO
     *
     * @param value TODO
     * @return TODO
     */
    protected BsonType getBsonType(Object value) {
        if (value == null) {
            return BsonType.NULL;
        } else if (value instanceof Boolean) {
            return BsonType.BOOLEAN;
        } else if (value instanceof Float) {
            return BsonType.DOUBLE;
        } else if (value instanceof Double) {
            return BsonType.DOUBLE;
        } else if (value instanceof Integer) {
            return BsonType.INT32;
        } else if (value instanceof Long) {
            return BsonType.INT64;
        } else if (value instanceof String) {
            return BsonType.STRING;
        } else if (isObjectIdInstance(value)) {
            return BsonType.OBJECT_ID;
        } else if (isObjectInstance(value)) {
            return BsonType.DOCUMENT;
        } else if (isArrayInstance(value)) {
            return BsonType.ARRAY;
        } else {
            return null;
        }
    }

    //------------------- Basic types

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readNull(BsonReader reader, DecoderContext ctx) {
        reader.readNull();
        return null;
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeNull(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        writer.writeNull();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readBoolean(BsonReader reader, DecoderContext ctx) {
        return reader.readBoolean();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeBoolean(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        writer.writeBoolean((Boolean) value);
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readDouble(BsonReader reader, DecoderContext ctx) {
        return reader.readDouble();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeDouble(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        if (value instanceof Float) {
            writer.writeDouble(((Float) value).doubleValue());
        } else {
            writer.writeDouble((double) value);
        }
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readInt32(BsonReader reader, DecoderContext ctx) {
        return reader.readInt32();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeInt32(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        writer.writeInt32((int) value);
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readInt64(BsonReader reader, DecoderContext ctx) {
        return reader.readInt64();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeInt64(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        if (value instanceof DataObject) {
            writeNumberLong(writer, name, value, ctx);
        } else {
            writer.writeInt64((long) value);
        }
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readString(BsonReader reader, DecoderContext ctx) {
        return reader.readString();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeString(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        writer.writeString((String) value);
    }

    /**
     * TODO
     *
     * @param instance TODO
     * @return TODO
     */
    protected abstract boolean isObjectIdInstance(Object instance);

    //-------------- JSON Object

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected O readDocument(BsonReader reader, DecoderContext ctx) {
        O object = newObject();

        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String name = reader.readName();
            add(object, name, readValue(reader, ctx));
        }
        reader.readEndDocument();

        return object;
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeDocument(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        @SuppressWarnings("unchecked")
        O object = (O) value;

        writer.writeStartDocument();

        Set<String> skip = new HashSet<>();
        if (ctx.isEncodingCollectibleDocument()) {
            beforeFields(object, (k, v) -> {
                skip.add(k);
                writer.writeName(k);
                writeValue(writer, k, v, ctx);
            });
        }

        forEach(object, (k, v) -> {
            if (!skip.contains(k)) {
                writer.writeName(k);
                writeValue(writer, k, v, ctx);
            }
        });

        writer.writeEndDocument();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    protected abstract O newObject();

    /**
     * TODO
     *
     * @param object TODO
     * @param name   TODO
     * @param value  TODO
     */
    protected abstract void add(O object, String name, Object value);

    /**
     * TODO
     *
     * @param instance TODO
     * @return TODO
     */
    protected abstract boolean isObjectInstance(Object instance);

    /**
     * TODO
     *
     * @param object         TODO
     * @param objectConsumer TODO
     */
    protected void beforeFields(O object, BiConsumer<String, Object> objectConsumer) {
    }

    /**
     * TODO
     *
     * @param object         TODO
     * @param objectConsumer TODO
     */
    protected abstract void forEach(O object, BiConsumer<String, Object> objectConsumer);

    //-------------- JSON Array

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected A readArray(BsonReader reader, DecoderContext ctx) {
        A array = newArray();

        reader.readStartArray();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            add(array, readValue(reader, ctx));
        }
        reader.readEndArray();

        return array;
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeArray(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        @SuppressWarnings("unchecked")
        A array = (A) value;

        writer.writeStartArray();
        forEach(array, o -> writeValue(writer, null, o, ctx));
        writer.writeEndArray();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    protected abstract A newArray();

    /**
     * TODO
     *
     * @param array TODO
     * @param value TODO
     */
    protected abstract void add(A array, Object value);

    /**
     * TODO
     *
     * @param instance TODO
     * @return TODO
     */
    protected abstract boolean isArrayInstance(Object instance);

    /**
     * TODO
     *
     * @param array         TODO
     * @param arrayConsumer TODO
     */
    protected abstract void forEach(A array, Consumer<Object> arrayConsumer);

    //-------------- Extended Mongo JSON types

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readBinary(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeBinary(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readDateTime(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeDateTime(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readDbPointer(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeDbPointer(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readMaxKey(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeMaxKey(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readMinKey(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeMinKey(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readJavaScript(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeJavaScript(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readJavaScriptWithScope(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeJavaScriptWithScope(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readObjectId(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeObjectId(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readRegularExpression(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeRegularExpression(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readSymbol(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeSymbol(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readTimeStamp(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeTimeStamp(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readUndefined(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeUndefined(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeNumberLong(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param writer TODO
     * @param name   TODO
     * @param value  TODO
     * @param ctx    TODO
     */
    protected void writeNumberDecimal(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * TODO
     *
     * @param reader TODO
     * @param ctx    TODO
     * @return TODO
     */
    protected Object readNumberDecimal(BsonReader reader, DecoderContext ctx) {
        throw new UnsupportedOperationException();
    }
}
