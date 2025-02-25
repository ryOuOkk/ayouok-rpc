package com.ayouok.config;

import com.ayouok.loadbalancer.LoadBalancer;
import com.ayouok.loadbalancer.constant.LoadBalancerKeys;
import com.ayouok.serializer.SerializerKeys;
import lombok.Data;

/**
 * @author dxn
 * @date 2024年12月20日 15:23
 * @apiNote
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 服务器主机名
     */
    private String serverHost;

    /**
     * 服务器端口号
     */
    private Integer serverPort;

    /**
     * 是否开启mock
     */
    private Boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
    /**
     * 负载均衡
     */
    private String loadBalancer = LoadBalancerKeys.CONSISTENT_HASH;

}
