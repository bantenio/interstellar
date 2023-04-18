package org.tenio.interstellar.functions;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FunctionUtils {
    private static final Map<Class<?>, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();

    SerializedLambda getSerializedLambda(Serializable fn) {
        SerializedLambda lambda = CLASS_LAMBDA_CACHE.get(fn.getClass());
        // 先检查缓存中是否已存在
        if (lambda == null) {
            try {
                // 提取SerializedLambda并缓存
                Method method = fn.getClass().getDeclaredMethod("writeReplace");
                method.setAccessible(Boolean.TRUE);
                lambda = (SerializedLambda) method.invoke(fn);
                CLASS_LAMBDA_CACHE.put(fn.getClass(), lambda);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lambda;
    }
}
