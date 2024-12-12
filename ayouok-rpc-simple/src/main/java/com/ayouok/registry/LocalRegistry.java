package com.ayouok.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dxn
 * @date 2024年12月12日 14:52
 * @apiNote 本地注册中心
 */
public class LocalRegistry {

    /**
     * 本地注册中心
     */
    private static final Map<String,Class<?>> map = new ConcurrentHashMap<>();

    /**
     * 注册
     * @param interfaceName 接口名称
     * @param implClass 实现类
     */
    public static void register(String interfaceName,Class<?> implClass){
        map.put(interfaceName,implClass);
    }

    /**
     * 获取
     * @param interfaceName 接口名称
     * @return 实现类
     */
    public static Class<?> get(String interfaceName){
        return map.get(interfaceName);
    }

    /**
     * 删除
     * @param interfaceName 接口名称
     */
    public static void delete(String interfaceName){
        map.remove(interfaceName);
    }

}
