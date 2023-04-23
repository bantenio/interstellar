package org.tenio.interstellar.coder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MappingCodeConvertor {
    private final Map<String, CodeConvertor> codeConvertorMap = new HashMap<>();

    public boolean contains(String key) {
        return codeConvertorMap.containsKey(key);
    }

    public CodeConvertor get(String key) {
        return codeConvertorMap.get(key);
    }

    public long decode(String key, String code) {
        return codeConvertorMap.get(key).decode(code);
    }

    public String encode(String key, long num) {
        return codeConvertorMap.get(key).encode(num);
    }

    public void put(String key, CodeConvertor sequenceGenerator) {
        codeConvertorMap.put(key, sequenceGenerator);
    }

    public CodeConvertor remove(String key) {
        return codeConvertorMap.remove(key);
    }

    public CodeConvertor getOrCreate(String key, Function<String, ? extends CodeConvertor> creator) {
        return codeConvertorMap.computeIfAbsent(key, creator);
    }
}
