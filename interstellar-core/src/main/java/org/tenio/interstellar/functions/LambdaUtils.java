package org.tenio.interstellar.functions;

import cn.hutool.core.text.CharSequenceUtil;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LambdaUtils {

    private static final Map<Class, Class> PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING = new HashMap<>();
    private static final Map<Class<?>, SerializedLambda> CLASS_LAMBDA_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, MethodType> METHOD_SIGN_METHOD_TYPE_MAPPING = new HashMap<>();
    private static final Map<String, MethodHandle> METHOD_SIGN_METHOD_HANDLE_MAPPING = new HashMap<>();
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    static {
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(boolean.class, Boolean.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(char.class, Character.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(byte.class, Byte.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(short.class, Short.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(int.class, Integer.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(long.class, Long.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(float.class, Float.class);
        PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.put(double.class, Double.class);
    }

    public SerializedLambda getSerializedLambda(Serializable fn) {
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

    public static MethodType getMethodType(Class<?> type,
                                           String methodName,
                                           Class<?>[] parameterClasses)
            throws NoSuchMethodException, IllegalAccessException {
        String methodSign = CharSequenceUtil.format("{}.{}({})", type.getName(), methodName, Arrays.toString(parameterClasses));
        MethodType methodType = METHOD_SIGN_METHOD_TYPE_MAPPING.get(methodSign);
        if (methodType == null) {
            MethodHandle mh = getMethodHandler(type, methodName, parameterClasses);
            methodType = mh.type();
            METHOD_SIGN_METHOD_TYPE_MAPPING.put(methodSign, methodType);
        }
        return methodType;
    }

    public static MethodHandle getMethodHandler(Class<?> type,
                                                String methodName,
                                                Class<?>[] parameterClasses)
            throws NoSuchMethodException, IllegalAccessException {
        String methodSign = CharSequenceUtil.format("{}.{}({})", type.getName(), methodName, Arrays.toString(parameterClasses));
        MethodHandle methodHandle = METHOD_SIGN_METHOD_HANDLE_MAPPING.get(methodSign);
        if (methodHandle == null) {
            Method method = type.getDeclaredMethod(methodName, parameterClasses);
            methodHandle = LOOKUP.unreflect(method);
            METHOD_SIGN_METHOD_HANDLE_MAPPING.put(methodSign, methodHandle);
        }
        return methodHandle;
    }

    public static MethodType convertPrimitiveType(Class returnType, Class[] parameterTypes) {
        if (PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.containsKey(returnType)) {
            returnType = PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.get(returnType);
        }
        Class[] targetParameterTypes = new Class[parameterTypes.length];
        for (int idx = 0; idx < parameterTypes.length; idx++) {
            Class parameterType = parameterTypes[idx];
            if (PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.containsKey(returnType)) {
                parameterType = PRIMITIVE_TYPE_OBJECT_TYPE_MAPPING.get(returnType);
            }
            targetParameterTypes[idx] = parameterType;
        }
        return MethodType.methodType(returnType, parameterTypes);
    }
}
