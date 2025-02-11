package com.ayouok.registry;

import com.ayouok.registry.impl.EtcdRegistry;
import com.ayouok.spi.SpiLoader;

/**
 * 注册中心工厂（用于获取注册中心对象）
 * @author ayouokk
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     *
     * @param key 注册中心key
     * @return  注册中心实例
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }

}
