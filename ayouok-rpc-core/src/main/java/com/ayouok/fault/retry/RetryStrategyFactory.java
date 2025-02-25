package com.ayouok.fault.retry;

import com.ayouok.fault.retry.impl.NoRetryStrategy;
import com.ayouok.spi.SpiLoader;

/**
 * @author dxn
 * @date 2025年02月25日 17:15
 * @apiNote 重试策略工厂
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试策略
     */
    private static final RetryStrategy DEFAULT_LOAD_BALANCER = new NoRetryStrategy();

    /**
     * 获取实例
     *
     * @param key key
     * @return 重试策略
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }

}
