package com.ayouok.ayouokrpcspringbootstarter.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author dxn
 * @date 2025年01月13日 20:51
 * @apiNote mock服务代理
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        log.info("mock service proxy invoke method:{}", method.getName());
        return getDefaultObject(returnType);
    }

    private Object getDefaultObject(Class<?> returnType) {
        if (returnType.isPrimitive()){
            if (returnType == boolean.class){
                return false;
            } else if (returnType == byte.class){
                return (byte) 0;
            } else if (returnType == char.class){
                return (char) 0;
            } else if (returnType == short.class){
                return (short) 0;
            } else if (returnType == int.class){
                return 0;
            } else if (returnType == long.class){
                return 0L;
            } else if (returnType == float.class){
                return 0.0F;
            } else if (returnType == double.class){
                return 0.0D;
            }
        }
        return null;
    }
}
