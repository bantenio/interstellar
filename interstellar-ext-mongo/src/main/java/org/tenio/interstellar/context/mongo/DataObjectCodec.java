package org.tenio.interstellar.context.mongo;

import org.bson.*;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.tenio.interstellar.context.DataArray;
import org.tenio.interstellar.context.DataObject;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class DataObjectCodec  extends AbstractDataCodec<DataObject, DataArray> implements CollectibleCodec<DataObject> {
    public static final String ID_FIELD = "_id";
    public static final String DATE_FIELD = "$date";
    public static final String BINARY_FIELD = "$binary";
    public static final String TYPE_FIELD = "$type";
    public static final String OID_FIELD = "$oid";
    public static final String LONG_FIELD = "$numberLong";
    public static final String DECIMAL_FIELD = "$numberDecimal";

    //https://docs.mongodb.org/manual/reference/mongodb-extended-json/#timestamp
    public static final String TIMESTAMP_FIELD = "$timestamp";
    public static final String TIMESTAMP_TIME_FIELD = "t";
    public static final String TIMESTAMP_INCREMENT_FIELD = "i";

    private boolean useObjectId = false;

    public DataObjectCodec(boolean useObjectId) {
        this.useObjectId = useObjectId;
    }

    @Override
    public DataObject generateIdIfAbsentFromDocument(DataObject json) {

        if (!documentHasId(json)) {
            String value = generateHexObjectId();
            if (useObjectId) json.put(ID_FIELD, new DataObject().put(OID_FIELD, value));
            else json.put(ID_FIELD, value);
        }
        return json;
    }

    public static String generateHexObjectId() {
        ObjectId id = new ObjectId();
        return id.toHexString();
    }

    @Override
    public boolean documentHasId(DataObject json) {
        return json.containsKey(ID_FIELD);
    }

    @Override
    public BsonValue getDocumentId(DataObject json) {
        if (!documentHasId(json)) {
            throw new IllegalStateException("The document does not contain an _id");
        }

        Object id = json.getValue(ID_FIELD);
        if (id instanceof String) {
            return new BsonString((String) id);
        }

        BsonDocument idHoldingDocument = new BsonDocument();
        BsonWriter writer = new BsonDocumentWriter(idHoldingDocument);
        writer.writeStartDocument();
        writer.writeName(ID_FIELD);
        writeValue(writer, null, id, EncoderContext.builder().build());
        writer.writeEndDocument();
        return idHoldingDocument.get(ID_FIELD);
    }

    @Override
    public Class<DataObject> getEncoderClass() {
        return DataObject.class;
    }

    @Override
    protected boolean isObjectIdInstance(Object instance) {
        if (instance instanceof DataObject && ((DataObject) instance).containsKey(OID_FIELD)) {
            return true;
        }
        return false;
    }

    @Override
    protected void beforeFields(DataObject object, BiConsumer<String, Object> objectConsumer) {
        if (object.containsKey(ID_FIELD)) {
            objectConsumer.accept(ID_FIELD, object.getValue(ID_FIELD));
        }
    }

    @Override
    protected DataObject newObject() {
        return new DataObject();
    }

    @Override
    protected void add(DataObject object, String name, Object value) {
        object.put(name, value);
    }

    @Override
    protected boolean isObjectInstance(Object instance) {
        return instance instanceof DataObject;
    }

    @Override
    protected void forEach(DataObject object, BiConsumer<String, Object> objectConsumer) {
        object.forEach(entry -> {
            objectConsumer.accept(entry.getKey(), entry.getValue());
        });
    }

    @Override
    protected DataArray newArray() {
        return new DataArray();
    }

    @Override
    protected void add(DataArray array, Object value) {
        if (value != null) {
            array.add(value);
        } else {
            array.addNull();
        }
    }

    @Override
    protected boolean isArrayInstance(Object instance) {
        return instance instanceof DataArray;
    }

    @Override
    protected void forEach(DataArray array, Consumer<Object> arrayConsumer) {
        array.forEach(arrayConsumer);
    }

    @Override
    protected BsonType getBsonType(Object value) {
        BsonType type = super.getBsonType(value);
        if (type == BsonType.DOCUMENT) {
            DataObject obj = (DataObject) value;
            if (obj.containsKey(DATE_FIELD)) {
                return BsonType.DATE_TIME;
            } else if (obj.containsKey(OID_FIELD)) {
                return BsonType.OBJECT_ID;
            } else if (obj.containsKey(BINARY_FIELD)) {
                return BsonType.BINARY;
            } else if (obj.containsKey(TIMESTAMP_FIELD)) {
                return BsonType.TIMESTAMP;
            } else if (obj.containsKey(LONG_FIELD)) {
                return BsonType.INT64;
            } else if (obj.containsKey(DECIMAL_FIELD)) {
                return BsonType.DECIMAL128;
            }
        }
        return type;
    }

    //---------- Support additional mappings

    @Override
    protected Object readObjectId(BsonReader reader, DecoderContext ctx) {
        return new DataObject().put(OID_FIELD, reader.readObjectId().toHexString());
    }

    @Override
    protected void writeObjectId(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        DataObject json = (DataObject) value;
        ObjectId objectId = new ObjectId(json.getString(OID_FIELD));
        writer.writeObjectId(objectId);
    }

    @Override
    protected Object readDateTime(BsonReader reader, DecoderContext ctx) {
        final DataObject result = new DataObject();
        result.put(DATE_FIELD,
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(reader.readDateTime()), ZoneOffset.UTC).format(ISO_OFFSET_DATE_TIME));
        return result;
    }

    @Override
    protected void writeDateTime(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        writer.writeDateTime(OffsetDateTime.parse(((DataObject) value).getString(DATE_FIELD)).toInstant().toEpochMilli());
    }

    @Override
    protected Object readBinary(BsonReader reader, DecoderContext ctx) {
        final DataObject result = new DataObject();
        BsonBinary bsonBinary = reader.readBinaryData();
        result.put(BINARY_FIELD, bsonBinary.getData())
                .put(TYPE_FIELD, bsonBinary.getType());
        return result;
    }

    @Override
    protected void writeBinary(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        DataObject binaryJsonObject = (DataObject) value;
        byte type = Optional.ofNullable(binaryJsonObject.getInteger(TYPE_FIELD))
                .map(Integer::byteValue)
                .orElse(BsonBinarySubType.BINARY.getValue());
        final BsonBinary bson = new BsonBinary(type, binaryJsonObject.getBinary(BINARY_FIELD));
        writer.writeBinaryData(bson);
    }

    @Override
    protected Object readTimeStamp(BsonReader reader, DecoderContext ctx) {
        final DataObject result = new DataObject();
        final DataObject timeStampComponent = new DataObject();

        final BsonTimestamp bson = reader.readTimestamp();

        timeStampComponent.put(TIMESTAMP_TIME_FIELD, bson.getTime());
        timeStampComponent.put(TIMESTAMP_INCREMENT_FIELD, bson.getInc());

        result.put(TIMESTAMP_FIELD, timeStampComponent);

        return result;
    }

    @Override
    protected void writeTimeStamp(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        final DataObject timeStamp = ((DataObject) value).getDataObject(TIMESTAMP_FIELD);

        final BsonTimestamp bson = new BsonTimestamp(timeStamp.getInteger(TIMESTAMP_TIME_FIELD),
                timeStamp.getInteger(TIMESTAMP_INCREMENT_FIELD));

        writer.writeTimestamp(bson);
    }

    @Override
    protected void writeNumberLong(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        final long aLong = ((DataObject) value).getLong(LONG_FIELD);
        writer.writeInt64(aLong);
    }

    @Override
    protected void writeNumberDecimal(BsonWriter writer, String name, Object value, EncoderContext ctx) {
        final String decimal = ((DataObject) value).getString(DECIMAL_FIELD);
        if (decimal.contains(".")) {
            writer.writeDecimal128(new Decimal128(new BigDecimal(decimal)));
        } else {
            writer.writeDecimal128(new Decimal128(Long.parseLong(decimal)));
        }
    }

    @Override
    protected Object readNumberDecimal(BsonReader reader, DecoderContext ctx) {
        final Decimal128 decimal128 = reader.readDecimal128();
        return decimal128.toString();
    }
}
