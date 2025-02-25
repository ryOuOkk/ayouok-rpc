package com.ayouok.loadbalancer;

import com.ayouok.loadbalancer.impl.RoundRobinLoadBalancer;
import com.ayouok.spi.SpiLoader;

/**
 * @author dxn
 * @date 2025年02月25日 17:15
 * @apiNote 负载均衡器工厂（工厂模式，用于获取负载均衡器对象）
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     *
     * @param key key
     * @return LoadBalancer
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }

}
