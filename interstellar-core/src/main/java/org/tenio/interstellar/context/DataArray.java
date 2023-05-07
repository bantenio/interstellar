package org.tenio.interstellar.context;

import org.tenio.interstellar.buffer.Buffer;
import org.tenio.interstellar.lang.Copyable;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * &#064;author sunkaihan
 */
public class DataArray implements Iterable<Object>, Copyable {
    private List<Object> list;

    public DataArray() {
        list = new ArrayList<>();
    }

    public DataArray(List list) {
        if (list == null) {
            throw new NullPointerException();
        }
        this.list = list;
    }

    public String getString(int pos) {
        Object val = list.get(pos);

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

    public Number getNumber(int pos) {
        return (Number) list.get(pos);
    }

    public Integer getInteger(int pos) {
        Number number = (Number) list.get(pos);
        if (number == null) {
            return null;
        } else if (number instanceof Integer) {
            return (Integer) number; // Avoids unnecessary unbox/box
        } else {
            return number.intValue();
        }
    }

    public Long getLong(int pos) {
        Number number = (Number) list.get(pos);
        if (number == null) {
            return null;
        } else if (number instanceof Long) {
            return (Long) number; // Avoids unnecessary unbox/box
        } else {
            return number.longValue();
        }
    }

    public Double getDouble(int pos) {
        Number number = (Number) list.get(pos);
        if (number == null) {
            return null;
        } else if (number instanceof Double) {
            return (Double) number; // Avoids unnecessary unbox/box
        } else {
            return number.doubleValue();
        }
    }

    public Float getFloat(int pos) {
        Number number = (Number) list.get(pos);
        if (number == null) {
            return null;
        } else if (number instanceof Float) {
            return (Float) number; // Avoids unnecessary unbox/box
        } else {
            return number.floatValue();
        }
    }

    public Boolean getBoolean(int pos) {
        return (Boolean) list.get(pos);
    }

    public DataObject getJsonObject(int pos) {
        Object val = list.get(pos);
        if (val instanceof Map) {
            val = new DataObject((Map) val);
            this.set(pos, val);
        }
        return (DataObject) val;
    }

    public DataArray getJsonArray(int pos) {
        Object val = list.get(pos);
        if (val instanceof List) {
            val = new DataArray((List) val);
            this.set(pos, val);
        }
        return (DataArray) val;
    }

    public byte[] getBinary(int pos) {
        Object val = list.get(pos);
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

    public Buffer getBuffer(int pos) {
        Object val = list.get(pos);
        // no-op
        if (val == null) {
            return null;
        }
        // no-op if value is already an Buffer
        if (val instanceof Buffer) {
            return (Buffer) val;
        }
        // wrap if value is already a byte[]
        if (val instanceof byte[]) {
            return Buffer.buffer((byte[]) val);
        }
        // assume that the value is in String format as per RFC
        String encoded = (String) val;
        // parse to proper type
        return Buffer.buffer(Utils.BASE64_DECODER.decode(encoded));
    }

    public Instant getInstant(int pos) {
        Object val = list.get(pos);
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

    public Object getValue(int pos) {
        return Utils.wrapJsonValue(list.get(pos));
    }

    public <T> T getValue(int pos, Class<T> clazz) {
        return clazz.cast(list.get(pos));
    }

    public boolean hasNull(int pos) {
        return list.get(pos) == null;
    }

    public DataArray addNull() {
        list.add(null);
        return this;
    }

    public DataArray add(Object value) {
        list.add(value);
        return this;
    }

    public DataArray add(int pos, Object value) {
        list.add(pos, value);
        return this;
    }

    public DataArray addAll(DataArray array) {
        list.addAll(array.list);
        return this;
    }

    public DataArray setNull(int pos) {
        list.set(pos, null);
        return this;
    }

    public DataArray set(int pos, Object value) {
        list.set(pos, value);
        return this;
    }

    public boolean contains(Object value) {
        return list.contains(value);
    }

    public boolean remove(Object value) {
        final Object wrappedValue = Utils.wrapJsonValue(value);
        for (int i = 0; i < list.size(); i++) {
            // perform comparision on wrapped types
            final Object otherWrapperValue = getValue(i);
            if (wrappedValue == null) {
                if (otherWrapperValue == null) {
                    list.remove(i);
                    return true;
                }
            } else {
                if (wrappedValue.equals(otherWrapperValue)) {
                    list.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public Object remove(int pos) {
        return Utils.wrapJsonValue(list.remove(pos));
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public List getList() {
        return list;
    }

    public DataArray clear() {
        list.clear();
        return this;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iter(list.iterator());
    }

    @Override
    public DataArray copy() {
        return copy(Utils.DEFAULT_CLONER);
    }

    public DataArray copy(Function<Object, ?> cloner) {
        List<Object> copiedList = new ArrayList<>(list.size());
        for (Object val : list) {
            copiedList.add(Utils.checkAndCopy(val, cloner));
        }
        return new DataArray(copiedList);
    }

    public Stream<Object> stream() {
        return DataObject.asStream(iterator());
    }

    @Override
    public boolean equals(Object o) {
        // null check
        if (o == null)
            return false;
        // self check
        if (this == o)
            return true;
        // type check and cast
        if (getClass() != o.getClass())
            return false;

        DataArray other = (DataArray) o;
        // size check
        if (this.size() != other.size())
            return false;
        // value comparison
        for (int i = 0; i < this.size(); i++) {
            Object thisValue = this.getValue(i);
            Object otherValue = other.getValue(i);
            // identity check
            if (thisValue == otherValue) {
                continue;
            }
            // special case for numbers
            if (thisValue instanceof Number && otherValue instanceof Number && thisValue.getClass() != otherValue.getClass()) {
                Number n1 = (Number) thisValue;
                Number n2 = (Number) otherValue;
                // floating point values
                if (thisValue instanceof Float || thisValue instanceof Double || otherValue instanceof Float || otherValue instanceof Double) {
                    // compare as floating point double
                    if (n1.doubleValue() == n2.doubleValue()) {
                        // same value check the next entry
                        continue;
                    }
                }
                if (thisValue instanceof Integer || thisValue instanceof Long || otherValue instanceof Integer || otherValue instanceof Long) {
                    // compare as integer long
                    if (n1.longValue() == n2.longValue()) {
                        // same value check the next entry
                        continue;
                    }
                }
            }
            // special case for char sequences
            if (thisValue instanceof CharSequence && otherValue instanceof CharSequence && thisValue.getClass() != otherValue.getClass()) {
                CharSequence s1 = (CharSequence) thisValue;
                CharSequence s2 = (CharSequence) otherValue;

                if (Objects.equals(s1.toString(), s2.toString())) {
                    // same value check the next entry
                    continue;
                }
            }
            // fallback to standard object equals checks
            if (!Objects.equals(thisValue, otherValue)) {
                return false;
            }
        }
        // all checks passed
        return true;
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    private static class Iter implements Iterator<Object> {

        final Iterator<Object> listIter;

        Iter(Iterator<Object> listIter) {
            this.listIter = listIter;
        }

        @Override
        public boolean hasNext() {
            return listIter.hasNext();
        }

        @Override
        public Object next() {
            return Utils.wrapJsonValue(listIter.next());
        }

        @Override
        public void remove() {
            listIter.remove();
        }
    }
}
