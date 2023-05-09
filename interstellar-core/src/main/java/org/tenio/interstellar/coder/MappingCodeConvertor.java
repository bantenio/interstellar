package org.tenio.interstellar.coder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * TODO
 * <p>
 * &#064;author:     Ban Tenio
 * &#064;version:    1.0
 */
public class MappingCodeConvertor {
    private final Map<String, CodeConvertor> codeConvertorMap = new HashMap<>();

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public boolean contains(String key) {
        return codeConvertorMap.containsKey(key);
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public CodeConvertor get(String key) {
        return codeConvertorMap.get(key);
    }

    /**
     * TODO
     *
     * @param key  TODO
     * @param code TODO
     * @return TODO
     */
    public long decode(String key, String code) {
        return codeConvertorMap.get(key).decode(code);
    }

    /**
     * TODO
     *
     * @param key TODO
     * @param num TODO
     * @return TODO
     */
    public String encode(String key, long num) {
        return codeConvertorMap.get(key).encode(num);
    }

    /**
     * TODO
     *
     * @param key               TODO
     * @param sequenceGenerator TODO
     */
    public void put(String key, CodeConvertor sequenceGenerator) {
        codeConvertorMap.put(key, sequenceGenerator);
    }

    /**
     * TODO
     *
     * @param key TODO
     * @return TODO
     */
    public CodeConvertor remove(String key) {
        return codeConvertorMap.remove(key);
    }

    /**
     * TODO
     *
     * @param key     TODO
     * @param creator TODO
     * @return TODO
     */
    public CodeConvertor getOrCreate(String key, Function<String, ? extends CodeConvertor> creator) {
        return codeConvertorMap.computeIfAbsent(key, creator);
    }
}
