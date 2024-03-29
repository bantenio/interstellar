package org.tenio.interstellar.context;

import org.tenio.interstellar.buffer.Buffer;
import org.tenio.interstellar.lang.Copyable;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class DataObject implements Iterable<Map.Entry<String, Object>>, Copyable {

    private Map<String, Object> map;

    /**
     *
     * TODO
     *
     */
    public DataObject() {
        map = new LinkedHashMap<>();
    }

    /**
     *
     * TODO
     *
     * @param map TODO
     */
    public DataObject(Map<String, Object> map) {
        if (map == null) {
            throw new NullPointerException();
        }
        this.map = map;
    }

    /**
     *
     * TODO
     *
     * @param key TODO
     * @param type TODO
     * @return TODO
     */
    public boolean keyWasType(String key, Class<?> type) {
        return map.containsKey(key) && map.get(key).getClass().isAssignableFrom(type);
    }

    /**
     *
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public String getString(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        if (val == null) {
            return null;
        }

        if (val instanceof Instant) {
            return ISO_INSTANT.format((Instant) val);
        } else if (val instanceof byte[]) {
            return Utils.BASE64_ENCODER.encodeToString((byte[]) val);
        } else if (val instanceof Buffer) {
            return Utils.BASE64_ENCODER.encodeToString(((Buffer) val).getBytes());
        } else if (val instanceof Enum) {
            return ((Enum) val).name();
        } else {
            return val.toString();
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Number getNumber(String key) {
        Objects.requireNonNull(key);
        return (Number) map.get(key);
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Integer getInteger(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Integer) {
            return (Integer) number;  // Avoids unnecessary unbox/box
        } else {
            return number.intValue();
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Long getLong(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Long) {
            return (Long) number;  // Avoids unnecessary unbox/box
        } else {
            return number.longValue();
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Double getDouble(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Double) {
            return (Double) number;  // Avoids unnecessary unbox/box
        } else {
            return number.doubleValue();
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Float getFloat(String key) {
        Objects.requireNonNull(key);
        Number number = (Number) map.get(key);
        if (number == null) {
            return null;
        } else if (number instanceof Float) {
            return (Float) number;  // Avoids unnecessary unbox/box
        } else {
            return number.floatValue();
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Boolean getBoolean(String key) {
        Objects.requireNonNull(key);
        return (Boolean) map.get(key);
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public DataObject getDataObject(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        if (val instanceof Map) {
            val = new DataObject((Map) val);
            this.put(key, val);
        }
        return (DataObject) val;
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public DataArray getDataArray(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        if (val instanceof List) {
            val = new DataArray((List) val);
            this.put(key, val);
        }
        return (DataArray) val;
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public byte[] getBinary(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        // no-op
        if (val == null) {
            return null;
        }
        // no-op if value is already an byte[]
        if (val instanceof byte[]) {
            return (byte[]) val;
        }
        // unwrap if value is already a Buffer
        if (val instanceof Buffer) {
            return ((Buffer) val).getBytes();
        }
        // assume that the value is in String format as per RFC
        String encoded = (String) val;
        // parse to proper type
        return Utils.BASE64_DECODER.decode(encoded);
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Buffer getBuffer(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        // no-op
        if (val == null) {
            return null;
        }
        // no-op if value is already an Buffer
        if (val instanceof Buffer) {
            return (Buffer) val;
        }

        // wrap if value is already an byte[]
        if (val instanceof byte[]) {
            return Buffer.buffer((byte[]) val);
        }

        // assume that the value is in String format as per RFC
        String encoded = (String) val;
        // parse to proper type
        return Buffer.buffer(Utils.BASE64_DECODER.decode(encoded));
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Instant getInstant(String key) {
        Objects.requireNonNull(key);
        Object val = map.get(key);
        // no-op
        if (val == null) {
            return null;
        }
        // no-op if value is already an Instant
        if (val instanceof Instant) {
            return (Instant) val;
        }
        // assume that the value is in String format as per RFC
        String encoded = (String) val;
        // parse to proper type
        return Instant.from(ISO_INSTANT.parse(encoded));
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Object getValue(String key) {
        Objects.requireNonNull(key);
        return Utils.wrapJsonValue(map.get(key));
    }

    /**
     * TODO
     *
     * @param key   TODO
     * @param clazz TODO
     * @param <T>   TODO
     * @return TODO
     */
    public <T> T getValue(String key, Class<T> clazz) {
        Objects.requireNonNull(key);
        return clazz.cast(map.get(key));
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public String getString(String key, String def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getString(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Number getNumber(String key, Number def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getNumber(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Integer getInteger(String key, Integer def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getInteger(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Long getLong(String key, Long def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getLong(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Double getDouble(String key, Double def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getDouble(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Float getFloat(String key, Float def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getFloat(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Boolean getBoolean(String key, Boolean def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getBoolean(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public DataObject getDataObject(String key, DataObject def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getDataObject(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public DataArray getDataArray(String key, DataArray def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getDataArray(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public byte[] getBinary(String key, byte[] def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getBinary(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Buffer getBuffer(String key, Buffer def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getBuffer(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Instant getInstant(String key, Instant def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getInstant(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param def TODO
     * @return TODO
     */
    public Object getValue(String key, Object def) {
        Objects.requireNonNull(key);
        if (map.containsKey(key)) {
            return getValue(key);
        } else {
            return def;
        }
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public boolean containsKey(String key) {
        Objects.requireNonNull(key);
        return map.containsKey(key);
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public Set<String> fieldNames() {
        return map.keySet();
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public DataObject putNull(String key) {
        Objects.requireNonNull(key);
        map.put(key, null);
        return this;
    }

    /**
     * TODO
     *
     * @param key   TODO
     * @param value TODO
     * @return TODO
     */
    public DataObject put(String key, Object value) {
        Objects.requireNonNull(key);
        map.put(key, value);
        return this;
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public Object remove(String key) {
        Objects.requireNonNull(key);
        return Utils.wrapJsonValue(map.remove(key));
    }

    /**
     * TODO
     *
     * @param other TODO
     * @return TODO
     */
    public DataObject mergeIn(DataObject other) {
        return mergeIn(other, false);
    }

    /**
     * TODO
     *
     * @param other TODO
     * @param deep  TODO
     * @return TODO
     */
    public DataObject mergeIn(DataObject other, boolean deep) {
        return mergeIn(other, deep ? Integer.MAX_VALUE : 1);
    }

    /**
     *
     * TODO
     *
     * @param other TODO
     * @param depth TODO
     * @return TODO
     */
    @SuppressWarnings("unchecked")
    public DataObject mergeIn(DataObject other, int depth) {
        if (depth < 1) {
            return this;
        }
        if (depth == 1) {
            map.putAll(other.map);
            return this;
        }
        for (Map.Entry<String, Object> e : other.map.entrySet()) {
            if (e.getValue() == null) {
                map.put(e.getKey(), null);
            } else {
                map.merge(e.getKey(), e.getValue(), (oldVal, newVal) -> {
                    if (oldVal instanceof Map) {
                        oldVal = new DataObject((Map) oldVal);
                    }
                    if (newVal instanceof Map) {
                        newVal = new DataObject((Map) newVal);
                    }
                    if (oldVal instanceof DataObject && newVal instanceof DataObject) {
                        return ((DataObject) oldVal).mergeIn((DataObject) newVal, depth - 1);
                    }
                    return newVal;
                });
            }
        }
        return this;
    }

    @Override
    public DataObject copy() {
        return copy(Utils.DEFAULT_CLONER);
    }

    /**
     *
     * @param cloner TODO
     * @return TODO
     */
    public DataObject copy(Function<Object, ?> cloner) {
        Map<String, Object> copiedMap;
        if (map instanceof LinkedHashMap) {
            copiedMap = new LinkedHashMap<>(map.size());
        } else {
            copiedMap = new HashMap<>(map.size());
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object val = Utils.checkAndCopy(entry.getValue(), cloner);
            copiedMap.put(entry.getKey(), val);
        }
        return new DataObject(copiedMap);
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public Map<String, Object> getMap() {
        return map;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public Stream<Map.Entry<String, Object>> stream() {
        return asStream(iterator());
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public int size() {
        return map.size();
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public DataObject clear() {
        map.clear();
        return this;
    }

    /**
     * TODO
     *
     * @return TODO
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return new Iter(map.entrySet().iterator());
    }

    private static class Iter implements Iterator<Map.Entry<String, Object>> {

        final Iterator<Map.Entry<String, Object>> mapIter;

        Iter(Iterator<Map.Entry<String, Object>> mapIter) {
            this.mapIter = mapIter;
        }

        @Override
        public boolean hasNext() {
            return mapIter.hasNext();
        }

        @Override
        public Map.Entry<String, Object> next() {
            final Map.Entry<String, Object> entry = mapIter.next();
            final Object val = entry.getValue();
            // perform wrapping
            final Object wrapped = Utils.wrapJsonValue(val);

            if (val != wrapped) {
                return new Entry(entry.getKey(), wrapped);
            }

            return entry;
        }

        @Override
        public void remove() {
            mapIter.remove();
        }
    }

    private static final class Entry implements Map.Entry<String, Object> {
        final String key;
        final Object value;

        public Entry(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public Object setValue(Object value) {
            throw new UnsupportedOperationException();
        }
    }

    static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
