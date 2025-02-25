package com.ayouok.fault.tolerant;

import com.ayouok.fault.tolerant.impl.FailFastTolerantStrategy;
import com.ayouok.spi.SpiLoader;

/**
 * @author ayouok
 * @create 2025-02-25-20:46
 * @Desc 容错机制工厂类
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错机制
     */
    private static final TolerantStrategy DEFAULT_LOAD_BALANCER = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key key
     * @return 容错机制
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }
}
