package com.ayouok.constant;

/**
 * @author dxn
 * @date 2025年02月25日 17:13
 * @apiNote 负载均衡器键名常量
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * 随机
     */
    String RANDOM = "random";

    /**
     * 一致性哈希
     */
    String CONSISTENT_HASH = "consistentHash";

}
