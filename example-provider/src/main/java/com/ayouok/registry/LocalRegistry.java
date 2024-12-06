package com.ayouok.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ayouok
 * @create 2024-12-06-15:53
 */
public class LocalRegistry {

    /**
     * 注册服务容器
     */
    private static final Map<String, Class<?>> REGISTRY = new ConcurrentHashMap<String, Class<?>>();

    /**
     * 注册服务
     *
     * @param className 服务类名称
     * @param clazz     服务类class
     */
    public static void register(String className, Class<?> clazz) {
        REGISTRY.put(className, clazz);
    }

    /**
     * 删除服务
     *
     * @param className 服务类名称
     * @param clazz     服务类class
     */
    public static void remove(String className, Class<?> clazz) {
        REGISTRY.remove(className);
    }

}
