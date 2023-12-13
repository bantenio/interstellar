package org.tenio.interstellar.example.module;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.tenio.interstellar.functions.LambdaUtils;

import java.lang.invoke.MethodHandle;

public class MethodHandleTestModule implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            Object object = this;
            MethodHandle methodHandle = LambdaUtils.getMethodHandler(MethodHandleTestModule.class, "add", new Class[]{int.class, int.class});
            int result = (int) methodHandle.invokeWithArguments(new Object[]{object, 1, 2});
            System.out.println("add result: " + result);

            methodHandle = LambdaUtils.getMethodHandler(MethodHandleTestModule.class, "sayHello", new Class[]{String.class});
            methodHandle.invokeWithArguments(new Object[]{object, "sunkaihan"});
        } catch (Throwable e) {
            throw new RuntimeException("MethodHandleTestModule run occurred error.", e);
        }
    }

    public int add(int a, int b) {
        return a + b;
    }

    public void sayHello(String name) {
        System.out.println("hello " + name);
    }
}
