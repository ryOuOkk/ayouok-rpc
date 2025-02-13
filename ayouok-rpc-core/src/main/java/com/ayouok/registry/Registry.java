package com.ayouok.registry;

import com.ayouok.config.RegistryConfig;
import com.ayouok.model.ServiceMetaInfo;

import java.util.List;

/**
 * @author ayouok
 * @create 2025-02-11-20:42
 * 注册中心接口
 */
public interface Registry {


    /**
     * 服务初始化
     */
    void init(RegistryConfig registryConfig);
    /**
     * 注册服务
     */
    void register(ServiceMetaInfo serviceMetaInfo);
    /**
     * 注销服务
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);
    /**
     * 服务发现
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);
    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测
     */
    void heartbeat();
}
