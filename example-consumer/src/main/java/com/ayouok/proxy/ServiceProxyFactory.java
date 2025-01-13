package com.ayouok.proxy;


import com.ayouok.config.RpcConfig;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 */

public class ServiceProxyFactory {

    public static <T> T getProxy(Class<T> serviceClass) {
        if (serviceClass == null) {
            throw new IllegalArgumentException("serviceClass cannot be null");
        }
        if (){
            return getMockProxy(serviceClass);
        }

        try {
            T proxyInstance = (T) Proxy.newProxyInstance(
                    serviceClass.getClassLoader(),
                    new Class[]{serviceClass},
                    new ServiceProxy());
            return proxyInstance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create proxy for " + serviceClass.getName(), e);
        }
    }

    private static <T> T getMockProxy(Class<T> serviceClass) {
        try {
            T proxyInstance = (T) Proxy.newProxyInstance(
                    serviceClass.getClassLoader(),
                    new Class[]{serviceClass},
                    new MockServiceProxy());
            return proxyInstance;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new RuntimeException("Failed to create proxy for " + serviceClass.getName(), e);
        }
    }

}
